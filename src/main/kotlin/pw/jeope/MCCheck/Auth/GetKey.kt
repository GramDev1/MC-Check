package pw.jeope.MCCheck.Auth

import pw.jeope.MCCheck.GUI.createIfNotExist
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardOpenOption
import java.util.*

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Wednesday, March, 2017
 */
object GetKey {

    val keyFile = File("${System.getProperty("user.home")}${File.separator}MC-Checker${File.separator}key.txt")
    fun getKeyFromSettings(): String? {
        println("Reading file ${keyFile.absolutePath} for key")
        if (! keyFile.exists()) return null;
        return Files.readAllLines(keyFile.toPath())[0]
    }

    fun createKey(key: String) {
        keyFile.createIfNotExist()
        Files.write(keyFile.toPath(), Arrays.asList(key), StandardOpenOption.WRITE)
        println("Created File: " + keyFile.absolutePath)
    }

    fun getValidKey(): Boolean {
        if (getKeyFromSettings() == null) {
            return false
        }
        return CheckKey.check(getKeyFromSettings()!!);
    }
}