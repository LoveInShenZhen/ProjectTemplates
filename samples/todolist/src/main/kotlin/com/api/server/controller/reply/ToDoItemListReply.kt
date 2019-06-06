package com.api.server.controller.reply

import jodd.datetime.JDateTime
import models.todolist.ToDoPriority
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase

class ToDoItemListReply : ReplyBase() {

    @Comment("待办事项列表")
    var items = listOf<ToDoItem>()

    override fun SampleData() {
        items = listOf(
            ToDoItem().apply {
                id = 99
                task = "改 bug: 9527"
                priority = ToDoPriority.High.code
                finished = true
                finish_time = JDateTime("2019-01-01 23:52:26")
            },
            ToDoItem().apply {
                id = 100
                task = "改 bug: 9528"
                priority = ToDoPriority.High.code
                finished = false
                finish_time = null
            }
        )
    }
}