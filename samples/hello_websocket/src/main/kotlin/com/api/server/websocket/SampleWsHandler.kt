package com.api.server.websocket

import com.api.server.websocket.message.*
import io.vertx.core.eventbus.Message
import io.vertx.core.eventbus.MessageConsumer
import io.vertx.core.http.ServerWebSocket
import jodd.datetime.JDateTime
import sz.scaffold.Application
import sz.scaffold.eventbus.BeanMessageCodec
import sz.scaffold.ext.failed
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.json.toShortJson
import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.Logger
import sz.scaffold.websocket.WebSocketHandler
import sz.scaffold.websocket.queryParams
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * 注: 该类的实现必须是线程安全的.
 *     fun handle(webSocket: ServerWebSocket) 方法会被多个线程同时调用
 */
@Suppress("UNUSED_PARAMETER")
class SampleWsHandler : WebSocketHandler {

    /**
     * 当服务端收到一个新的 webSocket 连接请求时, 系统调用此方法
     */
    override fun handle(webSocket: ServerWebSocket) {

        // 获取 qurey 参数
        val queryParams = webSocket.queryParams()
        // 模拟检查token参数, 检查不通过则拒绝连接
        val token = queryParams.get("token")
        if (checkToken(token).failed()) {
            webSocket.reject(1000)
            return
        }

        // 分配一个唯一的 clientID
        val clientId = UUID.randomUUID().toString()

        // 注册 EventBus 消息处理函数
        val consumers = listOf(
            registBroadcastMsgConsumer(clientId, webSocket),
            registUnicastMsgConsumer(clientId, webSocket)
        )

        webSocket.closeHandler {
            // Set a close handler. This will be called when the WebSocket is closed.
            Logger.debug("WebSocket: [$clientId] is closed.")
            consumers.forEach { it.unregister() }
            clientMap.remove(clientId)
        }.exceptionHandler {
            // Set an exception handler on the read stream.
            Logger.warn("WebSocket: [$clientId] 有异常发生:\n$it")
        }.textMessageHandler {
            Logger.debug("WebSocket: [$clientId] 接收到客户端发过来的消息:\n$it", AnsiColor.YELLOW)
            onTextMessage(clientId, webSocket, it)
        }.pongHandler {
            Logger.debug("收到客户端 [$clientId] 的心跳 ping")
        }

        Logger.debug("收到 client 端的 web socket 请求, 分配 clientId: $clientId and accept it")
        webSocket.accept()

        clientMap[clientId] = WsClientInfo(
            clientId = clientId,
            webSocket = webSocket,
            connect_time = JDateTime()
        )

        // 将分配的 clientId 返回给客户端
        webSocket.writeFinalTextFrame(WsMessage.newMessage(ConnectedMsg(clientId)).toJsonPretty())
    }

    private fun checkToken(token: String): Boolean {
        // 模拟检查 token, 不为空字符串就当做检查通过
        // 检查通过返回true
        Logger.debug("token : $token")
        return token.isNotBlank()
    }

    private fun onEventBusBroadcastMsg(clientId: String, webSocket: ServerWebSocket, message: Message<WsMessage>) {
        webSocket.writeFinalTextFrame(message.body().toJsonPretty())
    }

    private fun onEventBusUnicastMsg(clientId: String, webSocket: ServerWebSocket, message: Message<WsMessage>) {
        webSocket.writeFinalTextFrame(message.body().toJsonPretty())
    }

    private fun registBroadcastMsgConsumer(clientId: String, webSocket: ServerWebSocket): MessageConsumer<WsMessage> {
        return Application.vertx.eventBus().consumer<WsMessage>(broadcastBusAddress) { msg ->
            onEventBusBroadcastMsg(clientId, webSocket, msg)
        }
    }

    private fun registUnicastMsgConsumer(clientId: String, webSocket: ServerWebSocket): MessageConsumer<WsMessage> {
        return Application.vertx.eventBus().consumer<WsMessage>(unicastBusAddress(clientId)) { msg ->
            onEventBusUnicastMsg(clientId, webSocket, msg)
        }
    }

    private fun onTextMessage(clientId: String, webSocket: ServerWebSocket, message: String) {
        val wsmsg = WsMessage.parseElseNull(message)
        if (wsmsg == null) {
            val errmsg = WsMessage.newMessage(TextMsg("Invalid message format."))
            webSocket.writeTextMessage(errmsg.toJsonPretty())
        } else {
            when (wsmsg.type_name) {
                BroadcastTextMsg::class.java.name -> onClientBroadcastTextMsg(clientId, webSocket, wsmsg)
                UnicastMsg::class.java.name -> onClientUnicastMsg(clientId, webSocket, wsmsg)
                TextMsg::class.java.name -> onClientTextMsg(clientId, webSocket, wsmsg)
                else -> onUnSupportedType(clientId, webSocket, wsmsg)
            }
        }
    }

    /**
     * 客户端通过 websocket 向后端发送一个请求, 向所有的客户端广播一段文本信息
     */
    private fun onClientBroadcastTextMsg(clientId: String, webSocket: ServerWebSocket, message: WsMessage) {
        broadcastMessage(message)
    }

    /**
     * 客户端通过 websocket 向后端发送一个请求, 向指定的另外一个客户端发送一段文本信息
     */
    private fun onClientUnicastMsg(clientId: String, webSocket: ServerWebSocket, message: WsMessage) {
        val msg = message.bodyObject() as UnicastMsg
        unicastMessage(msg.receiver, WsMessage.newMessage(UnicastMsg(msg.text, clientId, msg.receiver)))
    }

    private fun onClientTextMsg(clientId: String, webSocket: ServerWebSocket, message: WsMessage) {
        val msg = message.bodyObject() as TextMsg
        Logger.info("收到客户端[$clientId] 发来的消息: ${msg.text}")
    }

    /**
     * 处理不被支持的 WsMessage.type_name
     */
    private fun onUnSupportedType(clientId: String, webSocket: ServerWebSocket, message: WsMessage) {
        Logger.warn("UnSupported WsMessage.type_name: ${message.type_name}")
        val msg = WsMessage.newMessage(TextMsg("Unsupported message type: ${message.type_name}"))
        webSocket.writeTextMessage(msg.toJsonPretty())

    }

    companion object {

        val clientMap: ConcurrentMap<String, WsClientInfo> = ConcurrentHashMap()

        const val broadcastBusAddress = "websocket.SampleWsHandler.broadcast"

        init {
            Application.vertx.eventBus().registerDefaultCodec(WsMessage::class.java, BeanMessageCodec(WsMessage::class.java))
        }

        /**
         * 根据 clientId 得到该连接对应的 event bus 上的消息地址
         */
        fun unicastBusAddress(clientId: String): String {
            return "websocket.SampleWsHandler.unicast.$clientId"
        }

        /**
         * 通过 EventBus 广播一条消息, 最终会发送到所有的 websocket 客户端上
         */
        fun broadcastMessage(message: WsMessage) {
            Application.vertx.eventBus().publish(broadcastBusAddress, message)
        }

        /**
         * 通过 EventBus 单播一条消息, 最终会发送到由 clientId 指定的 websocket 客户端上
         */
        fun unicastMessage(clientId: String, message: WsMessage) {
            Application.vertx.eventBus().send(unicastBusAddress(clientId), message)
        }
    }
}