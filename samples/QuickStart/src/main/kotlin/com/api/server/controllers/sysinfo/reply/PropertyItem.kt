package com.api.server.controllers.sysinfo.reply

import sz.scaffold.annotations.Comment

//
// Created by kk on 2019-05-21.
//
class PropertyItem {

    @Comment("Property属性的名称")
    var name = ""

    @Comment("Property属性的值")
    var value = ""
}