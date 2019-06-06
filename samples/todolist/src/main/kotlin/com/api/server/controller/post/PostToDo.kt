package com.api.server.controller.post

import models.todolist.ToDoPriority
import sz.scaffold.annotations.Comment


class PostToDo {

    @Comment("待办事项内容")
    var task = ""

    @Comment("待办事项优先级, 1-低, 2-普通, 3-高")
    var priority = 0

    @Comment("待办事项是否已完成, 新增待办事项的时候, 请设置为false")
    var finished: Boolean = false

    fun SampleData() {
        task = "实现 fun SampleData() { TODO(\"填充样例数据\") } 方法"
        priority = ToDoPriority.High.code
        finished = false
    }
}