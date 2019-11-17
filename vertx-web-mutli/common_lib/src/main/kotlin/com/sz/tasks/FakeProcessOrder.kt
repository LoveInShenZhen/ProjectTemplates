package com.sz.tasks

import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2019/11/17.
//
@Suppress("PropertyName", "MemberVisibilityCanBePrivate")
class FakeProcessOrder(val order_no: String) : Runnable {

    override fun run() {
        Logger.debug("任务模拟处理订单: $order_no")
    }

}