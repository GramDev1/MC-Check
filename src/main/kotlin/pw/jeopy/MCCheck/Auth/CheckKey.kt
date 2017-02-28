package pw.jeopy.MCCheck.Auth

import com.mashape.unirest.http.Unirest

/**
 * This file was created by @author thejp for the use of
For Profit. Please note, all rights to code are retained by
afore mentioned thejp unless otherwise stated.
File created: Sunday, February, 2017
 */
object CheckKey {
    fun check(key : String) : Boolean {

        @SuppressWarnings("unchecked")
        val response = Unirest.get("https://mc-check.jp78.me/authorize?key=$key").asObject(Map::class.java).body as Map<String,Any>
        return response["success"] as Boolean

    }
}