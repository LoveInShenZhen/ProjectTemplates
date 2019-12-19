package com.task.server

import sz.AsynTask.AsyncTasksVerticle
import sz.PlanTaskService
import sz.ebean.SzEbeanConfig
import sz.scaffold.Application


object PlanTaskServer {

    @JvmStatic
    fun main(args: Array<String>) {

        SzEbeanConfig.loadConfig()
        Application.setupVertx()

        Application.regOnStartHandler(10) {
            PlanTaskService.start()
            AsyncTasksVerticle.deploy(Application.vertx)
        }

        Application.regOnStopHanlder(10) {
            PlanTaskService.stop()
            AsyncTasksVerticle.unDeploy()
        }

        Application.setupOnStartAndOnStop()
    }
}
