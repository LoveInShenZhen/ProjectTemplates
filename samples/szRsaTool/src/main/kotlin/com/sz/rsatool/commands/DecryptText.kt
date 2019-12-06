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
class DecryptText: CliktCommand(name = "decrypt", help = "Decrypt text") {

    private val privateFile by option("-k", "--privateKey", help = "RSA private key file path").file(exists = true, folderOkay = false, readable = true).required()
    private val encryptedTxt by argument(name = "-- encryptedTxt")

    override fun run() {
        val privateKeyPem = privateFile.readText()
        val privateKey = RsaUtil.privateKeyFromPem(privateKeyPem)
        echo(RsaUtil.decrypt(encryptedTxt, privateKey).cliColor(AnsiColor.GREEN))
    }
}