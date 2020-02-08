package com.sample

import com.examples.GreeterGrpc
import com.examples.HelloReply
import com.examples.HelloRequest
import io.vertx.core.Promise
import sz.scaffold.tools.logger.Logger
import java.util.concurrent.ForkJoinPool

//
// Created by kk on 2019-07-29.
//
@Suppress("DEPRECATION")
class GreeterVertxService : GreeterGrpc.GreeterVertxImplBase() {
    override fun sayHello(request: HelloRequest, response: Promise<HelloReply>) {
        ForkJoinPool.commonPool().submit {
            try {
                val replyMsg = "你好! ${request.name}, 请开始你的表演!"
                Logger.debug(replyMsg)
                response.complete(HelloReply.newBuilder().setMessage(replyMsg).build())
            } catch (ex: Exception) {
                Logger.debug(ex.message!!)
                response.fail(ex)
            }
        }
    }
}