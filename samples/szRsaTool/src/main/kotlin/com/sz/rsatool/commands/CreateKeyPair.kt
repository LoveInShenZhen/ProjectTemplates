package com.sz.rsatool.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import jodd.datetime.JDateTime
import sz.crypto.RsaUtil
import sz.scaffold.ext.filePathJoin
import sz.scaffold.tools.logger.AnsiColor
import sz.scaffold.tools.logger.cliColor
import java.io.File

//
// Created by kk on 2019/12/6.
//
@Suppress("MemberVisibilityCanBePrivate")
class CreateKeyPair : CliktCommand(name = "createKeyPair", help = "Create rsa public/private key file in dest dir.") {

    val destDir by option("-d", "--destDir", help = "output dir").file(fileOkay = false, writable = true).default(File("."))

    override fun run() {
        destDir.mkdirs()

        val keyPair = RsaUtil.createPemKeyPair()
        val timeStr = JDateTime().toString("YYYY_MM_DD-hhmmss")
        val publicKeyPath = filePathJoin(destDir.absolutePath, "publicKey-$timeStr.pem")
        val privateKeyPath = filePathJoin(destDir.absolutePath, "privateKey-$timeStr.pem")
        File(publicKeyPath).writeText(keyPair.first)
        File(privateKeyPath).writeText(keyPair.second)

        echo("Create RSA public key file:   $publicKeyPath".cliColor(AnsiColor.GREEN))
        echo("Create RSA private key file:  $privateKeyPath".cliColor(AnsiColor.GREEN))
    }
}