package net.jeopy.MCCheck.CheckingUtils

import com.mashape.unirest.http.Unirest

/**
 *
 */
class CheckRunnable(var batch: List<String>) : Runnable {

    /**
     * Run the checking operation for our batch list
     */
    override fun run() {
        if (batch.size > 5) throw IllegalStateException("The batch list cannot be greater than 5!")
        batch.forEach {
            if (available(it)) AvailableNames.available.add(it)
        }

    }

    /**
     * Check if a username is available. Returns true for available and false for not
     */
    fun available(name: String): Boolean {
        val url = "https://api.mojang.com/users/profiles/minecraft/$name"
        return  Unirest.get(url).asString().status == 204

    }
}