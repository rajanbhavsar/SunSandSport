package com.sunsandsports.network.Client

import com.google.gson.JsonObject

object HttpCommonMethod {

    /**
     * check Error Message
     */
    fun getErrorMessage(error: JsonObject?): String {
        var value = ""
        if (error != null) {
            val obj = error.asJsonObject // since you know it's a JsonObject
            try {
                if (obj != null) {
                    val entries = obj.entrySet() // will return members of your object
                    for ((key, value1) in entries) {
                        println(value1)
                    }
                }
            } catch (e: Exception) {
                value = ""
            }
        }
        return value
    }
}