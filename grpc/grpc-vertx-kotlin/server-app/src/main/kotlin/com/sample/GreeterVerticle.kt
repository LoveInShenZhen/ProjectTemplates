package com.sample

import io.vertx.core.AbstractVerticle
import io.vertx.grpc.VertxServerBuilder
import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2019-07-29.
//
class GreeterVerticle : AbstractVerticle() {

    override fun start() {
        val port = 8888

        val server = VertxServerBuilder.forAddress(vertx, "localhost", port)
            .addService(GreeterVertxService())
            .build()
        server.start()
        Logger.debug("Server started, listening on $port")
    }

}