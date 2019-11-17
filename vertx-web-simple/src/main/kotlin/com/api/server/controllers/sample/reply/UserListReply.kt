package com.api.server.controllers.sample.reply

import jodd.datetime.JDateTime
import jodd.datetime.ext.toJDateTime
import models.sample.User
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase
import java.util.*

//
// Created by kk on 2019/11/15.
//
class UserListReply : ReplyBase() {

    @Comment("用户列表")
    var users = listOf<DUser>()
}

data class DUser(val user_id: UUID,
                 val user_name: String,
                 val remarks: String,
                 val create_time: JDateTime) {

    companion object {

        fun buildFrom(user: User): DUser {
            return DUser(
                user_id = user.user_id,
                user_name = user.name,
                remarks = user.remarks ?: "",
                create_time = user.whenCreated!!.toJDateTime()
            )
        }

    }
}