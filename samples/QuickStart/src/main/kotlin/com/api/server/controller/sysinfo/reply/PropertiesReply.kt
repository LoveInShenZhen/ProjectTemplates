package com.api.server.controller.sysinfo.reply

import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase


class PropertiesReply : ReplyBase() {

    @Comment("系统属性列表")
    var properties = listOf<PropertyItem>()

    fun load() {
        properties = System.getProperties().map {
            val item = PropertyItem()
            item.name = it.key.toString()
            item.value = it.value.toString()
            item
        }
    }

    override fun SampleData() {
        this.load()
    }
}