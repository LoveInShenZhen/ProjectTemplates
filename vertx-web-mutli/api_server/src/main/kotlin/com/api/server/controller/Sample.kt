package com.api.server.controller

import com.api.server.controller.reply.HelloReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.tools.logger.Logger


@Comment("测试样例代码")
class Sample : ApiController() {

    @Comment("测试接口")
    fun hello(@Comment("访问者名称") name: String): HelloReply {
        val reply = HelloReply()

        reply.msg = "Hello $name, 准备就绪, 请开始你的表演!"

        Logger.debug(reply.msg)

        return reply
    }

}