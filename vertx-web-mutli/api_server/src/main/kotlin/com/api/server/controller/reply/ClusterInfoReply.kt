package com.api.server.controller.reply

import com.sun.org.apache.xpath.internal.operations.Bool
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
}