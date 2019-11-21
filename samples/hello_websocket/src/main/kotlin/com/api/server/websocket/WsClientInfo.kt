package com.api.server.websocket

import io.vertx.core.http.ServerWebSocket
import jodd.datetime.JDateTime

//
// Created by kk on 2019/11/20.
//
data class WsClientInfo(
    val clientId: String,
    val webSocket: ServerWebSocket,
    val connect_time: JDateTime
)