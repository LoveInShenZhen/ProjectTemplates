package com.api.server.websocket.message

//
// Created by kk on 2019/11/21.
//
data class UnicastMsg(
    // 消息文本
    val text: String,
    // 消息发送者
    val sender: String,
    // 消息接受者
    val receiver: String
)