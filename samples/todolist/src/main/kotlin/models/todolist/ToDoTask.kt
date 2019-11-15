package models.todolist

import io.ebean.Finder
import io.ebean.Model
import io.ebean.Query
import io.ebean.annotation.WhenCreated
import io.ebean.annotation.WhenModified
import jodd.datetime.JDateTime
import models.todolist.query.QToDoTask
import sz.ebean.DB
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

@Suppress("MemberVisibilityCanBePrivate")
@Entity
class ToDoTask(dataSource: String = "") : Model(dataSource) {

    @Id
    var id: Long = 0

    @Version
    var version: Long? = null

    @WhenCreated
    var whenCreated: Timestamp? = null

    @WhenModified
    var whenModified: Timestamp? = null

    @Column(columnDefinition = "TEXT COMMENT '任务描述'")
    var task = ""

    @Column(columnDefinition = "INT COMMENT '任务优先级, 1-低, 2-普通, 3-高'")
    var priority = 0        // 对应 enumeration: ToDoPriority

    @Column(columnDefinition = "TINYINT(1) COMMENT '是否已完成'")
    var finished: Boolean = false

    @Column(columnDefinition = "DATETIME COMMENT '任务完成时间'")
    var finish_time: JDateTime? = null

    @Column(columnDefinition = "TINYINT(1) COMMENT '标记删除'")
    var deleted: Boolean = false

    fun markFinished(newStatus: Boolean) {
        if (finished != newStatus) {
            if (newStatus) {
                // 未完成 ==> 完成
                finished = true
                finish_time = JDateTime()
            } else {
                // 完成 ==> 未完成
                finished = false
                finish_time = null
            }
        }
    }

    companion object {

        fun finder(dsName: String = DB.currentDataSource()): Finder<Long, ToDoTask> {
            return DB.finder(dsName)
        }

        fun query(dsName: String = DB.currentDataSource()): Query<ToDoTask> {
            return finder(dsName).query()
        }

        fun queryBean(dsName: String = DB.currentDataSource()): QToDoTask {
            return QToDoTask(DB.byDataSource(dsName))
        }
    }
}
