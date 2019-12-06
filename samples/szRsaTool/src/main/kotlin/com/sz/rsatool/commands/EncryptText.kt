package com.sz.rsatool.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import sz.crypto.RsaUtil
import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.cliColor

//
// Created by kk on 2019/12/6.
//
class EncryptText: CliktCommand(name = "encrypt", help = "Encrypt text") {

    private val publicKeyFile by option("-k", "--publicKey", help = "RSA public key file path").file(exists = true, folderOkay = false, readable = true).required()
    private val plainTxt by argument(name = "'plainTxt'")

    override fun run() {
        val publicKeyPem = publicKeyFile.readText()
        val publicKey = RsaUtil.publicKeyFromPem(publicKeyPem)
        echo(RsaUtil.encrypt(plainTxt, publicKey).cliColor(AnsiColor.GREEN))
    }
}