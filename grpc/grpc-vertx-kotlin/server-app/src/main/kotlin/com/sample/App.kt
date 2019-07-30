/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.sample

import io.vertx.core.DeploymentOptions
import sz.scaffold.Application

fun main(args: Array<String>) {

    Application.setupVertx()

    Application.vertx.deployVerticle(GreeterVerticle::class.java, DeploymentOptions().setInstances(1))

}
