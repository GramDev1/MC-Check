package pw.jeope.MCCheck.GUI

import com.google.common.io.Files
import pw.jeope.MCCheck.Auth.CheckKey
import pw.jeope.MCCheck.Auth.GetKey
import java.io.File
import java.nio.charset.Charset
import java.util.*

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Saturday, February, 2017
 */
object ConsoleGUI : BaseGUI() {

    /**
     * Initiates the Console GUI
     */
    @JvmStatic
    fun init() {
        var verified = GetKey.getValidKey();
        val s: Scanner = Scanner(System.`in`)
        while (! verified) {
            say("Please enter a valid  MC-Check key")
            val key = s.nextLine()
            verified = CheckKey.check(key)
            if(verified) GetKey.createKey(key)

        }
        say("Please enter the file path to the list of usernames")
        val list = File(s.nextLine())
        if (! list.exists()) {
            println("That file doesn't exist! Please enter one that does")
            return
        }
        val names: MutableList<String> = Files.readLines(list, Charset.defaultCharset())
        say("Extracted names from file...")
        say("Please enter the file path to the list of available usernames")
        val available = File(s.nextLine())
        available.createIfNotExist()
        this.init(names, available)


    }

    override fun say(message: String) {
        println(message)
    }


}


