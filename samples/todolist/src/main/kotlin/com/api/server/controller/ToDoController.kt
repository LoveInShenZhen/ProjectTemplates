package com.api.server.controller

import com.api.server.controller.post.PostToDo
import com.api.server.controller.reply.ToDoItem
import com.api.server.controller.reply.ToDoItemListReply
import com.api.server.controller.reply.ToDoItemReply
import models.todolist.ToDoPriority
import models.todolist.ToDoTask
import sz.DB
import sz.RunInTransaction
import sz.interceptors.EbeanTransaction
import sz.scaffold.annotations.Comment
import sz.scaffold.annotations.PostForm
import sz.scaffold.annotations.PostJson
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.reply.ReplyBase
import sz.scaffold.tools.BizLogicException


@Comment("待办事项管理")
class ToDoController : ApiController() {

    @Comment("新增一个待办事项")
    @PostForm(PostToDo::class)
    @EbeanTransaction
    fun newToDo(): ReplyBase {
        val reply = ReplyBase()
        val postData = postFormToBean<PostToDo>()

        checkPriority(postData.priority)
        checkTask(postData.task)

        val newToDo = ToDoTask()
        newToDo.task = postData.task
        newToDo.priority = postData.priority
        newToDo.finished = false

        newToDo.save()

        return reply
    }

    @Comment("根据待办事项ID查询指定的代码事项")
    fun byId(@Comment("待办事项ID") id: Long): ToDoItemReply {
        val reply = ToDoItemReply()

        // 采用QueryBean的方式进行查询
        val todoTask = ToDoTask.queryBean()
            .id.eq(id)
            .deleted.eq(false)
            .findOne() ?: throw BizLogicException("待办事项(id: $id)不存在或者已经被标记删除")

        // 采用Finder的方式进行查询
//        val todoTask = ToDoTask.query().where()
//            .eq("id", id)
//            .eq("deleted", false)
//            .findOne() ?: throw BizLogicException("待办事项(id: $id)不存在或者已经被标记删除")

        reply.item = ToDoItem.buildFrom(todoTask)

        return reply
    }

    @Comment("获取所有的待办事项")
    fun allTodos(): ToDoItemListReply {
        val reply = ToDoItemListReply()

        // 采用QueryBean的方式进行查询
        val todoTasks = ToDoTask.queryBean()
            .where()
            .deleted.eq(false)
            .orderBy("whenCreated ASC")
            .findList()

        // 采用Finder的方式进行查询
//        val todoTasks = ToDoTask.query().where()
//            .eq("deleted", false)
//            .orderBy("whenCreated ASC")
//            .findList()

        reply.items = todoTasks.map { task ->
            val item = ToDoItem.buildFrom(task)!!
            item
        }

        return reply
    }

    @Comment("更新指定的待办事项")
    @PostJson(PostToDo::class)
    fun updateToDo(@Comment("待办事项ID") id: Long): ReplyBase {
        return DB.byContext().RunInTransaction<ReplyBase> {
            val reply = ReplyBase()
            val postData = postJsonToBean<PostToDo>()

            checkPriority(postData.priority)
            checkTask(postData.task)

            val todo = ToDoTask.query().where()
                .eq("id", id)
                .eq("deleted", false)
                .findOne()

            if (todo == null || todo.deleted) {
                throw BizLogicException("待办事项(id: $id)不存在或者已经被标记删除")
            }

            todo.task = postData.task
            todo.priority = postData.priority
            todo.markFinished(postData.finished)

            todo.save()

            return@RunInTransaction reply
        }
    }

    @Comment("删除指定的待办事项")
    @EbeanTransaction
    fun delete(@Comment("待办事项ID") id: Long): ReplyBase {
        val reply = ReplyBase()
        val todo = ToDoTask.queryBean()
            .where()
            .id.eq(id)
            .deleted.eq(false)
            .findOne() ?: throw BizLogicException("待办事项(id: $id)不存在或者已经被标记删除")

        todo.deleted = true
        todo.save()

        return reply
    }

    @Comment("设置指定的待办事项为完成状态")
    @EbeanTransaction
    fun setFinisheStatus(@Comment("待办事项ID") id: Long,
                         @Comment("待办事项完成状态") finished: Boolean): ReplyBase {
        val reply = ReplyBase()
        val todo = ToDoTask.queryBean()
            .id.eq(id)
            .findOne() ?: throw BizLogicException("待办事项(id: $id)不存在")

        todo.markFinished(finished)
        todo.save()

        return reply
    }

    private fun checkPriority(priorityCode: Int) {
        if (ToDoPriority.verify(priorityCode).not()) {
            throw BizLogicException("Invalid ToDoPriority code: $priorityCode")
        }
    }

    private fun checkTask(task: String) {
        if (task.isEmpty()) {
            throw BizLogicException("待办事项内容不能为空")
        }
    }
}