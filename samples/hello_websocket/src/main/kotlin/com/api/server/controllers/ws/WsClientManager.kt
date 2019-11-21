package com.api.server.controllers.ws

import com.api.server.controllers.ws.reply.ClientsReply
import com.api.server.websocket.SampleWsHandler
import com.api.server.websocket.message.TextMsg
import com.api.server.websocket.message.WsMessage
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.reply.ReplyBase
import sz.scaffold.tools.BizLogicException

//
// Created by kk on 2019/11/21.
//

@Comment("websocket 客户端管理接口")
class WsClientManager : ApiController() {

    @Comment("查询当前连接的客户端列表")
    suspend fun listClients(): ClientsReply {
        val reply = ClientsReply()
        reply.load()
        return reply
    }

    @Comment("向指定的客户端发送一段文本信息")
    suspend fun sendTextTo(@Comment("待发送的文本消息") msg: String,
                           @Comment("接收消息的客户端ID") receiverId: String): ReplyBase {
        val reply = ReplyBase()
        val receiverInfo = SampleWsHandler.clientMap[receiverId]
        if (receiverInfo == null) {
            throw BizLogicException("Invalid receiverId.")
        } else {
            SampleWsHandler.unicastMessage(receiverId, WsMessage.newMessage(TextMsg(msg)))
        }
        return reply
    }

    @Comment("向所有的客户端广播一段文本信息")
    suspend fun sendTextToAll(@Comment("待发送的文本消息") msg: String): ReplyBase {
        val reply = ReplyBase()
        SampleWsHandler.broadcastMessage(WsMessage.newMessage(TextMsg(msg)))
        return reply
    }

    @Comment("强制断掉指定的客户端的连接")
    suspend fun closeClient(@Comment("客户端ID") clientId: String): ReplyBase {
        val reply = ReplyBase()
        val clientInfo = SampleWsHandler.clientMap[clientId]
        if (clientInfo == null) {
            throw BizLogicException("Invalid client id.")
        } else {
            clientInfo.webSocket.close()
        }
        return reply
    }
}