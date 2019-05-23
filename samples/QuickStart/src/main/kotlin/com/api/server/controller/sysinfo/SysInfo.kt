package com.api.server.controller.sysinfo

import com.api.server.controller.sysinfo.reply.EnvironmentReply
import com.api.server.controller.sysinfo.reply.MemoryUsageReply
import com.api.server.controller.sysinfo.reply.PropertiesReply
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController


@Comment("查询系统信息")
class SysInfo : ApiController() {

    @Comment("查询系统当前运行环境下的环境变量")
    fun environments() : EnvironmentReply {
        val reply = EnvironmentReply()
        reply.load()

        return reply
    }

    @Comment("查询系统当前运行环境下的属性列表")
    fun sysProperties() : PropertiesReply {
        val reply = PropertiesReply()
        reply.load()
        return reply
    }

    @Comment("查询系统当前的内存使用情况")
    fun memUsage() : MemoryUsageReply {
        val reply = MemoryUsageReply()
        reply.load()
        return reply
    }
}