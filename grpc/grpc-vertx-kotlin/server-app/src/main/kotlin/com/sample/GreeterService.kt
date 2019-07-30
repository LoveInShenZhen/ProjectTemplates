package com.sample

import com.examples.GreeterGrpc
import com.examples.HelloReply
import com.examples.HelloRequest
import io.grpc.stub.StreamObserver
import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2019-07-28.
//
class GreeterService : GreeterGrpc.GreeterImplBase() {

    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
        val replyMsg = "你好! ${request.name}, 请开始你的表演!"
        Logger.debug(replyMsg)
        responseObserver.onNext(HelloReply.newBuilder().setMessage(replyMsg).build())
        responseObserver.onCompleted()
    }
}