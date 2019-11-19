package com.api.server.controllers.sysinfo.reply

import sz.scaffold.annotations.Comment
import java.lang.management.MemoryUsage

class MemoryUsageInfo {

    @Comment("JVM分配的初始内存量；或者，如果未定义，则为 -1 (单位:MB)")
    var init_memory: Long = -1

    @Comment("可以使用的最大内存量；或者，如果未定义，则为 -1 (单位:MB)")
    var max_memory: Long = -1

    @Comment("表示JVM当前已经使用的内存量 (单位:MB)")
    var used_memory: Long = 0

    @Comment("已经提交的内存量 (单位:MB)")
    var committed: Long = 0

    fun loadFrom(usage: MemoryUsage): MemoryUsageInfo {
        init_memory = convertToMB(usage.init)
        max_memory = convertToMB(usage.max)
        used_memory = convertToMB(usage.used)
        committed  = convertToMB(usage.committed)
        return this
    }

    private fun convertToMB(size: Long): Long {
        if (size < 0) {
            return size
        } else {
            return size / 1024 / 1024
        }
    }
}