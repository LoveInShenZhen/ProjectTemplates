package com.api.server.controllers.sample.reply

import sz.scaffold.annotations.Comment
import sz.scaffold.controller.reply.ReplyBase

//
// Created by kk on 2019/11/17.
//
@Suppress("PropertyName")
class ClusterInfoReply : ReplyBase() {

    @Comment("是否在集群模式下")
    var in_cluster: Boolean = false

    @Comment("集群模式下, 实例对应的节点ID")
    var node_id : String = ""

    @Comment("集群模式下, 集群内所有的节点ID")
    var cluster_nodes = setOf<String>()

    override fun SampleData() {
        in_cluster = true
        node_id = "5eec2dd5-2f74-4b9c-baee-7d456bad6acc"
        cluster_nodes = setOf("5eec2dd5-2f74-4b9c-baee-7d456bad6acc", "c701eaab-4571-4ff8-8cbf-3b5fcbd22d1a")
    }
}