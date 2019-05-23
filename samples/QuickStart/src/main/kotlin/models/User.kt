package models

import io.ebean.Finder
import io.ebean.annotation.WhenCreated
import io.ebean.annotation.WhenModified
import models.query.QUser
import sz.DB
import sz.EntityBean.BaseModel
import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Version

//
// This is a sample model class.
//

@Entity
class User : BaseModel() {

    @Id
    var id: Long = 0

    @Version
    var version: Long? = null

    @WhenCreated
    var whenCreated: Timestamp? = null

    @WhenModified
    var whenModified: Timestamp? = null

    @Column(columnDefinition = "VARCHAR(32) COMMENT '用户名'", nullable = false)
    var name: String = ""

    @Column(columnDefinition = "VARCHAR(32) COMMENT '用户密码,明文,为null表示未设置密码'")
    var pwd: String? = null

    @Column(columnDefinition = "TEXT COMMENT '备注信息'")
    var remarks: String? = null

    companion object : Finder<Long, User>(User::class.java) {

        fun finder(dsName: String = DB.currentDataSource()) : Finder<Long, User> {
            return finder<Long, User>(dsName)
        }

        fun queryBean(dsName: String = DB.currentDataSource()): QUser {
            return QUser(DB.byDataSource(dsName))
        }

        fun findByName(name: String): User? {
            return queryBean().name.eq(name).findOne()
        }

        fun valid(name: String, pwd: String): Boolean {
            return queryBean()
                    .name.eq(name)
                    .pwd.eq(pwd)
                    .findCount() > 0
        }
    }
}