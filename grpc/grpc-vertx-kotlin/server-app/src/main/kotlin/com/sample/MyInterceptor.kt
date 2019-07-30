package com.sample

import io.grpc.Metadata
import io.grpc.ServerCall
import io.grpc.ServerCallHandler
import io.grpc.ServerInterceptor
import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2019-07-30.
//
class MyInterceptor : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(call: ServerCall<ReqT, RespT>?, headers: Metadata?, next: ServerCallHandler<ReqT, RespT>): ServerCall.Listener<ReqT> {
        Logger.debug("MyInterceptor.interceptCall(...)")
        return next.startCall(call, headers)
    }
}