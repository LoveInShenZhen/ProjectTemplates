package com.api.server.controllers.todolist.reply

import jodd.datetime.JDateTime
import models.todolist.ToDoTask
import sz.scaffold.annotations.Comment


class ToDoItem {

    @Comment("待办事项ID")
    var id: Long = 0

    @Comment("待办事项内容")
    var task = ""

    @Comment("待办事项优先级, 1-低, 2-普通, 3-高")
    var priority = 0

    @Comment("待办事项是否已完成")
    var finished: Boolean = false

    @Comment("待办事项完成时间")
    var finish_time: JDateTime? = null

    companion object {

        fun buildFrom(todoTask: ToDoTask?): ToDoItem? {
            if (todoTask != null) {
                val item = ToDoItem()
                item.id = todoTask.id
                item.task = todoTask.task
                item.priority = todoTask.priority
                item.finished = todoTask.finished
                item.finish_time = todoTask.finish_time

                return item
            } else {
                return null
            }
        }
    }
}