package com.api.server.controllers.sysinfo.reply

import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase


class EnvironmentReply : ReplyBase() {

    @Comment("运行环境当前所有的环境变量, Key 为环境变量名称, Value 为环境变量的值")
    var environments = mutableMapOf<String, String>()

    fun load() {
        environments = System.getenv()
    }

    override fun SampleData() {
        this.load()
    }
}