package com.api.server.controllers.sample

import com.api.server.controllers.sample.reply.ClusterInfoReply
import com.api.server.controllers.sample.reply.DUser
import com.api.server.controllers.sample.reply.HelloReply
import com.api.server.controllers.sample.reply.UserListReply
import com.sz.tasks.FakeProcessOrder
import com.sz.tasks.FakeSendSms
import jodd.datetime.JDateTime
import jodd.util.RandomString
import models.PlanTask
import models.sample.User
import sz.AsynTask.AsyncTask
import sz.ebean.DB
import sz.ebean.DDL
import sz.ebean.runTransactionBlocking
import sz.scaffold.Application
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.controller.ContentTypes
import sz.scaffold.controller.reply.ReplyBase
import sz.scaffold.tools.BizLogicException
import sz.scaffold.tools.json.toJsonPretty
import sz.scaffold.tools.logger.Logger
import java.util.*

/**
 * 注: 以下样例, 都省略了参数的合法性校验.
 *     在实际的开发编码工作中, 需要对参数加上参数有效性校验(参数是否有值, 取值范围, 格式是否有效等等...)
 */

@Suppress("DuplicatedCode")
@Comment("测试样例代码")
class SampleController : ApiController() {

    @Comment("测试接口")
    suspend fun hello(@Comment("访问者名称") name: String): HelloReply {
        val reply = HelloReply()

        reply.msg = "Hello $name, 准备就绪, 请开始你的表演!"

        Logger.debug(reply.msg)

        return reply
    }

    @Comment("查询节点的集群信息")
    suspend fun clusterInfo(): ClusterInfoReply {
        val reply = ClusterInfoReply()

        reply.in_cluster = Application.vertx.isClustered
        if (reply.in_cluster) {
            reply.node_id = Application.vertxOptions.clusterManager.nodeID
            reply.cluster_nodes = Application.vertxOptions.clusterManager.nodes.toSet()
        }

        return reply
    }

    @Suppress("UNREACHABLE_CODE")
    @Comment("数据库操作测试: 嵌套事务方式,新增2个用户,用户信息随机生成")
    suspend fun newUser(): ReplyBase {
        val reply = ReplyBase()
        Logger.debug("准备数据库操作")

        // 以下代码, 是数据库事务嵌套事务的写法样例
        DB.runTransactionAwait(dataSource = "db1") { ebeanServer ->
            Logger.debug("level 0 current data source: ${DB.currentDataSource()}")
            val user = User.new()
            user.user_id = UUID.randomUUID()
            user.name = RandomString.get().randomAlpha(8) + "☺️"    // 测试utf8mb4是否正常
            user.remarks = "level 0: Create time: ${JDateTime()}"
            user.updatePwd("123456")

            ebeanServer.runTransactionBlocking {
                Logger.debug("level 1 current data source: ${DB.currentDataSource()}")
                val user2 = User.new()
                user2.user_id = UUID.randomUUID()
                user2.name = RandomString.get().randomAlpha(8) + "🚗"
                user2.remarks = "level 1: Create time: ${JDateTime()}"
                user2.updatePwd("123456")

                user2.save()

                Logger.debug("level 1 new user:\n${user2.toJsonPretty()}")
                Logger.debug("total count: ${User.finder().all().count()}")
//                throw BizLogicException("模拟出现异常,异常会rollback整个事务,包括外层事务, 异常发生在线程 [${Thread.currentThread().name}] 上")
            }

            user.save()
            Logger.debug("level 0 new user:\n${user.toJsonPretty()}")
            Logger.debug("total count: ${User.finder().all().count()}")
        }
        Logger.debug("数据库操作完成")
        return reply
    }

    @Comment("数据库查询测试: 查询用户列表, 代码里显示指定数据库事务为ReadOnly,优化性能")
    suspend fun userList(): UserListReply {
        val reply = UserListReply()

        DB.runTransactionAwait(readOnly = true) {
            reply.users = User.finder().all().map {
                DUser.buildFrom(it)
            }
        }

        return reply
    }

    @Suppress("IfThenToElvis")
    @Comment("数据库删除操作测试: 根据用户UUID, 删除指定用户")
    suspend fun delUser(@Comment("用户UUID") user_id: String): ReplyBase {
        val reply = ReplyBase()

        DB.runTransactionAwait {
            val user = User.queryBean().where()
                .user_id.eq(UUID.fromString(user_id))
                .findOne()

            if (user == null) {
                throw BizLogicException("Invalid user id")
            } else {
                user.delete()
            }
        }

        return reply
    }

    @Comment("返回创建数据库的ddl sql")
    suspend fun createDdl(): String {
        contentType(ContentTypes.Text)
        return DDL.createDdl()
    }

    @Comment("返回删除数据库的ddl sql")
    suspend fun dropAllDdl(): String {
        contentType(ContentTypes.Text)
        return DDL.dropAllDdl()
    }

    /**
     * 发送短消息这类任务, 没有持久化的必要性. 处理的过程, 也不涉及到数据库的事务(是指在处理的过程中, 没有需要持久化到数据库的事务)
     * 对任务执行的成功性, 没有那么严格的要求. 执行失败, 不会产生破坏性的副作用
     * 此类任务, 可以使用 AsyncTask 来将任务提交给 async_task_server 去异步执行.
     */
    @Comment("向async_task_server 提交一个向指定手机发送短消息的异步任务")
    suspend fun sendSms(@Comment("手机号码") mobile: String,
                        @Comment("消息内容") message: String): ReplyBase {
        val reply = ReplyBase()
        val task = FakeSendSms(mobile = mobile, msg = message)
        AsyncTask.submit(task)
        return reply
    }

    /**
     *
     */
    @Comment("向async_task_server 提交一个处理指定订单号的异步任务")
    suspend fun processOrder(@Comment("订单号") order_no: String): ReplyBase {
        val  reply = ReplyBase()
        val task = FakeProcessOrder(order_no = order_no)
        PlanTask.addTaskAwait(task)
        return reply
    }
}