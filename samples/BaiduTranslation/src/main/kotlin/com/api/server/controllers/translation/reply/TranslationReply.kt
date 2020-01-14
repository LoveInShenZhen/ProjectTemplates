package com.api.server.controllers.translation.reply

import com.fasterxml.jackson.databind.JsonNode
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase

//
// Created by kk on 2020/1/14.
//
class TranslationReply : ReplyBase() {

    @Comment("翻译 api 返回的 json 结果")
    var result: JsonNode? = null
}