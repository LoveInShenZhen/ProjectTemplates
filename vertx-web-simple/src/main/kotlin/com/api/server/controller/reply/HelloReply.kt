package com.api.server.controller.reply

import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase


class HelloReply : ReplyBase() {

    @Comment("响应消息")
    var msg = ""

    override fun SampleData() {
        msg = "接口返回的信息"
    }
}