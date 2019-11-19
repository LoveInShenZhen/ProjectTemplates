package com.api.server

import sz.ebean.SzEbeanConfig
import sz.scaffold.Application


object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        SzEbeanConfig.loadConfig()

        Application.setupVertx()

        Application.runHttpServer()
        Application.setupOnStartAndOnStop()
    }

}
