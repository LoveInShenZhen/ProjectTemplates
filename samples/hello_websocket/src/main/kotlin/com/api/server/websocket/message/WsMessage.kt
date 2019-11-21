package com.api.server.websocket.message

import com.fasterxml.jackson.databind.JsonNode
import io.netty.util.CharsetUtil
import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import sz.scaffold.tools.json.Json
import sz.scaffold.tools.json.toShortJson

//
// Created by kk on 2019/11/20.
//

// 该类用来封装 web socket client 端 和 server 端之间的消息
data class WsMessage(
    // 消息的标识, 在 Request-Response 模式下, 对应 Request 消息和 Response消息. 不需要如此用时, 可以忽略, 设为null
    val msg_id: Long?,

    // 消息类型名称
    val type_name: String,

    // 消息体
    val body: JsonNode
) {

    fun bodyObject(): Any {
        val clazz = WsMessage::class.java.classLoader.loadClass(type_name)
        return Json.fromJsonNode(body, clazz)
    }

    companion object {

        fun newMessage(dataObj: Any, msgId: Long? = null): WsMessage {
            return WsMessage(
                msg_id = msgId,
                type_name = dataObj.javaClass.name,
                body = Json.toJson(dataObj)
            )
        }

        fun parse(jsonMsg: String): WsMessage {
            return Json.fromJsonString(jsonMsg, WsMessage::class.java)
        }

        fun parseElseNull(jsonMsg: String): WsMessage? {
            return try {
                Json.fromJsonString(jsonMsg, WsMessage::class.java)
            } catch (ex: Exception) {
                null
            }

        }
    }
}

class WsMessageCodec : MessageCodec<WsMessage, WsMessage> {

    override fun decodeFromWire(pos: Int, buffer: Buffer): WsMessage {
        val length = buffer.getInt(pos)
        val startPos = pos + 4
        val bytes = buffer.getBytes(startPos, startPos + length)
        val jsonStr = String(bytes, CharsetUtil.UTF_8)
        return Json.fromJsonString(jsonStr)
    }

    override fun systemCodecID(): Byte {
        return -1
    }

    override fun encodeToWire(buffer: Buffer, s: WsMessage) {
        val jsonStr = s.toShortJson()
        val strBytes: ByteArray = jsonStr.toByteArray(CharsetUtil.UTF_8)
        buffer.appendInt(strBytes.size)
        buffer.appendBytes(strBytes)
    }

    override fun transform(s: WsMessage): WsMessage {
        return s
    }

    override fun name(): String {
        return "WsMessage"
    }

}