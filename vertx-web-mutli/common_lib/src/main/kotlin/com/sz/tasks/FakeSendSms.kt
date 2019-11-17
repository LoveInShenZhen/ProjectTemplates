package com.sz.tasks

import sz.scaffold.tools.logger.Logger

//
// Created by kk on 2019/11/17.
//
@Suppress("MemberVisibilityCanBePrivate")
class FakeSendSms(val mobile: String, val msg: String) : Runnable {

    override fun run() {
        Logger.debug("""任务模拟向手机: $mobile 发送消息: "$msg" """)
    }

}