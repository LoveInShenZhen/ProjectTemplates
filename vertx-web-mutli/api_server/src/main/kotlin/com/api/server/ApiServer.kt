package com.api.server

import sz.ebean.SzEbeanConfig
import sz.scaffold.Application

object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        SzEbeanConfig.loadConfig()

        Application.setupVertx()

//        Application.regOnStartHandler(50) {
//
//        }

//        Application.regOnStopHanlder {
//
//        }

        Application.runHttpServer()
        Application.setupOnStartAndOnStop()
    }

}
