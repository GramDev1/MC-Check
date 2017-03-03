package pw.jeope.MCCheck.GUI

import java.io.File

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Sunday, February, 2017
 */
fun File.createIfNotExist(parent: Boolean = true) {
    if (! this.exists()) {
        if (parent) createWithDirs() else this.createNewFile()
    }
}

fun File.createWithDirs() {
    this.parentFile.mkdirs()
    this.createNewFile()
}
