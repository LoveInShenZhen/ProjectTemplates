/*
 * Knowing the Name of Your Main Class
 * ref: https://stackoverflow.com/questions/14733566/how-to-run-kotlin-class-from-the-command-line?answertab=votes#tab-top
 */
package com.sz.first

import jodd.io.FileNameUtil
import jodd.io.FileUtil
import org.apache.zookeeper.server.ZooKeeperServerMain
import org.h2.tools.Server
import sz.scaffold.Application
import sz.scaffold.ext.filePathJoin
import sz.scaffold.tools.logger.Logger

fun main(args: Array<String>) {
    // 初始化并启动 H2 Database Server
    val h2dbPath = FileNameUtil.concat(Application.appHome, "h2db")
    FileUtil.mkdirs(h2dbPath)

    val h2Port = "9527"
    val h2server = Server.createTcpServer("-tcpPort", h2Port, "-tcpAllowOthers", "-ifNotExists", "-baseDir", h2dbPath)
    h2server.start()
    Logger.info("Start h2 database server on localhost:$h2Port")

    // 启动一个 standalone ZooKeeperServer
    val zooCfgPath = filePathJoin(Application.appHome, "conf", "zoo.cfg")
    ZooKeeperServerMain.main(arrayOf(zooCfgPath))
}
