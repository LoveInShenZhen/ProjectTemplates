package com.api.server.controller.sysinfo.reply

import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase
import java.lang.management.ManagementFactory


class MemoryUsageReply : ReplyBase() {

    @Comment("堆内存使用情况")
    var heapUsage = MemoryUsageInfo()

    @Comment("非堆内存使用情况")
    var nonHeapUsage = MemoryUsageInfo()

    fun load() {
        val mxBean = ManagementFactory.getMemoryMXBean()
        heapUsage.loadFrom(mxBean.heapMemoryUsage)
        nonHeapUsage.loadFrom(mxBean.nonHeapMemoryUsage)
    }

    override fun SampleData() {
        this.load()
    }
}