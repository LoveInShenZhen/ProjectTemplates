package com.api.server.controllers.sample

import com.api.server.controllers.sample.reply.MessageReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.tools.logger.Logger


@Suppress("DuplicatedCode")
@Comment("测试样例代码")
class SampleController : ApiController() {

    @Comment("测试接口")
    suspend fun hello(@Comment("访问者名称") name: String): MessageReply {
        val reply = MessageReply()

        reply.msg = "Hello $name, 准备就绪, 请开始你的表演!"

        Logger.debug(reply.msg)

        return reply
    }
}