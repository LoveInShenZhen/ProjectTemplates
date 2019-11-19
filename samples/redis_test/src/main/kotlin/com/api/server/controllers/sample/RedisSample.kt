package com.api.server.controllers.sample

import com.api.server.controllers.sample.reply.MessageReply
import sz.scaffold.annotations.Comment
import sz.scaffold.cache.redis.RedisAsyncCache
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.reply.ReplyBase

//
// Created by kk on 2019/11/20.
//

@Comment("Redis 缓存测试")
class RedisSample : ApiController() {

    @Comment("Redis缓存 set 测试")
    suspend fun setCache(@Comment("缓存键") key: String,
                         @Comment("缓存值") value: String,
                         @Comment("缓存超时时间") timeOut: Long): ReplyBase {
        val reply = ReplyBase()
        RedisAsyncCache.default.setAwait(key, value, timeOut)
        return reply
    }

    @Comment("Redis缓存 get 测试")
    suspend fun getCache(@Comment("缓存键") key: String): MessageReply {
        val reply = MessageReply()
        reply.msg = RedisAsyncCache.default.getOrElseAwait(key) {
            "缓存不存在"
        }
        return reply
    }

    @Comment("Redis缓存 del 测试")
    suspend fun delCache(@Comment("缓存键") key: String): ReplyBase {
        val reply = ReplyBase()
        RedisAsyncCache.default.delAwait(key)
        return reply
    }

    @Comment("Redis缓存, 测试指定key的缓存项是否存在")
    suspend fun existsCache(@Comment("缓存键") key: String): MessageReply {
        val reply = MessageReply()
        reply.msg = if (RedisAsyncCache.default.existsAwait(key)) "存在" else "不存在"
        return reply
    }
}