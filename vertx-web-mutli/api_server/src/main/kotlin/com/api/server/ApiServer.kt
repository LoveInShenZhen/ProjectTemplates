package com.api.server

import jodd.io.FileNameUtil
import jodd.io.FileUtil
import org.h2.tools.Server
import sz.ebean.SzEbeanConfig
import sz.scaffold.Application
import sz.scaffold.tools.logger.Logger

object ApiServer {

    @JvmStatic
    fun main(args: Array<String>) {
        val h2dbPath = FileNameUtil.concat(Application.appHome, "h2db")
        FileUtil.mkdirs(h2dbPath)

        val h2Port = "9123"
        val h2server = Server.createTcpServer("-tcpPort", h2Port, "-tcpAllowOthers", "-ifNotExists", "-baseDir", h2dbPath)
        h2server.start()
        Logger.info("Start h2 database server on localhost:$h2Port")

        SzEbeanConfig.loadConfig()

        Application.setupVertx()

//        Application.regOnStartHandler(50) {
//
//        }

        Application.regOnStopHanlder {
            h2server.stop()
            Logger.info("Stop h2 database server on localhost:$h2Port")
        }

        Application.runHttpServer()
        Application.setupOnStartAndOnStop()
    }

}
