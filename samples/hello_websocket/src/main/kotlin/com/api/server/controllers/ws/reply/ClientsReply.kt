package com.api.server.controllers.ws.reply

import com.api.server.websocket.SampleWsHandler
import jodd.datetime.JDateTime
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase

//
// Created by kk on 2019/11/21.
//
class ClientsReply : ReplyBase() {

    @Comment("连接的客户端列表")
    var clients = listOf<ClientInfo>()

    fun load(): ClientsReply {
        clients = SampleWsHandler.clientMap.values.map {
            ClientInfo(it.clientId, it.connect_time)
        }.toList()

        return this
    }
}

data class ClientInfo(
    @Comment("分配的客户端ID")
    val clientId: String,

    @Comment("连接时间")
    val connect_time: JDateTime
)