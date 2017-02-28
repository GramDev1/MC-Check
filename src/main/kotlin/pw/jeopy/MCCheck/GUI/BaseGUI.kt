package pw.jeopy.MCCheck.GUI

import com.google.common.collect.Lists
import pw.jeopy.MCCheck.CheckingUtils.AvailableNames
import pw.jeopy.MCCheck.CheckingUtils.CheckRunnable
import java.io.File
import java.nio.file.StandardOpenOption
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Sunday, February, 2017
 */
abstract class BaseGUI {
    private val CheckService = Executors.newCachedThreadPool()
    protected fun init(names: List<String>, available: File) {
        val validNames = names.filter { checkValid(it) }
        val partions = split(validNames)
        say("Split list by 5s, running ${partions.size} threads")
        partions.forEach {
            CheckService.submit(CheckRunnable(it))
        }
        say("Please wait, checking...")
        CheckService.shutdown() //Make it stop....
        CheckService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS) //Wait till its done
        say("Checking complete, writing to output file")
        java.nio.file.Files.write(available.toPath(), AvailableNames.available, StandardOpenOption.APPEND)
    }

    abstract fun say(message: String);
    /**
     * Converts a list into multiple lists of 5 each
     */
    protected fun split(names: List<String>): List<List<String>> {
        return Lists.partition(names, 5);


    }

    private fun checkValid(name: String) : Boolean{
         if (name.length >= 3) return false
         val pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(name).find()


    }
}