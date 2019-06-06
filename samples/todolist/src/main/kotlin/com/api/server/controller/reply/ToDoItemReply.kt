package com.api.server.controller.reply

import jodd.datetime.JDateTime
import models.todolist.ToDoPriority
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase

//
// Created by kk on 2019-06-05.
//
class ToDoItemReply : ReplyBase() {

    @Comment("待办事项")
    var item: ToDoItem? = null

    override fun SampleData() {
        item = ToDoItem()
        item!!.id = 99
        item!!.task = "改 bug: 9527"
        item!!.priority = ToDoPriority.High.code
        item!!.finished= true
        item!!.finish_time = JDateTime("2019-01-01 23:52:26")
    }
}