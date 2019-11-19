package com.api.server

import sz.scaffold.Application


object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        Application.setupVertx()

        Application.runHttpServer()
    }

}
