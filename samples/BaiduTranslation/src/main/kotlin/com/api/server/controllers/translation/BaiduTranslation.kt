package com.api.server.controllers.translation

import com.api.server.controllers.translation.reply.TranslationReply
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.ext.web.client.sendAwait
import sz.scaffold.Application
import sz.scaffold.annotations.Comment
import sz.scaffold.controller.ApiController
import sz.scaffold.tools.BizLogicException
import sz.scaffold.tools.json.Json
import kotlin.random.Random

/**
 * 百度翻译 api 文档请参考: http://api.fanyi.baidu.com/api/trans/product/apidoc
 */
class BaiduTranslation : ApiController() {

    @Comment("翻译 英文 到 中文")
    suspend fun enToCh(qTxt: String): TranslationReply {
        val salt = Random.nextInt()
        val response = webClient.getAbs(apiUrl)
            .addQueryParam("q", qTxt)
            .addQueryParam("from", "auto")
            .addQueryParam("to", "zh")
            .addQueryParam("appid", appId)
            .addQueryParam("salt", salt.toString())
            .addQueryParam("sign", sign(qTxt, salt))
            .sendAwait()

        if (response.statusCode() != 200) {
            throw BizLogicException("Baidu 翻译 api 返回错误: ${response.statusCode()} : ${response.statusMessage()}")
        }

        val reply = TranslationReply()
        reply.result = Json.parse(response.bodyAsString())

        return reply
    }

    /**
     * 签名生成方法如下：
     * 1、将请求参数中的 APPID(appid), 翻译query(q, 注意为UTF-8编码), 随机数(salt), 以及平台分配的密钥(可在管理控制台查看)
     * 按照 appid+q+salt+密钥 的顺序拼接得到字符串1。
     * 2、对字符串1做md5，得到32位小写的sign。
     */
    private fun sign(qTxt: String, salt: Int): String {
        val s = appId + qTxt + salt.toString() + appKey
        return jodd.crypt.DigestEngine.md5().digestString(s).toLowerCase()
    }

    companion object {
        val appId = Application.config.getString("translation.baidu.appId")
        val appKey = Application.config.getString("translation.baidu.appKey")
        val apiUrl = Application.config.getString("translation.baidu.apiUrl")

        /**
         * In most cases, a Web Client should be created once on application startup and then reused.
         * Otherwise you lose a lot of benefits such as connection pooling and may leak resources
         * if instances are not closed properly.
         *
         * ref: https://vertx.io/docs/vertx-web-client/kotlin/
         */
        val webClient = WebClient.create(Application.vertx)
    }
}