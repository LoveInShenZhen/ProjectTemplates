/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.sample

import io.grpc.ServerBuilder
import sz.scaffold.tools.logger.Logger

fun main(args: Array<String>) {
    val port = 8888
    val server = ServerBuilder.forPort(port).addService(GreeterService()).build()
    server.start()
    Logger.debug("Server started, listening on $port")
    server.awaitTermination()
}
