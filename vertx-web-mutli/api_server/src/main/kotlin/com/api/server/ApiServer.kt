package com.api.server

import sz.SzEbeanConfig
import sz.scaffold.Application

object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        SzEbeanConfig.loadConfig()

        Application.setupVertx()

//        Application.regOnStartHandler(50) {
//
//        }

        Application.runHttpServer()

    }

}
