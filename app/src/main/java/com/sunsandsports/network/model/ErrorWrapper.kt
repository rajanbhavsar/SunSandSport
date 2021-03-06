package com.basekotlin.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This is the common error wrapper class to define the error response
 */
class ErrorWrapper {
    @SerializedName("errors")
    @Expose
    var errors: JsonObject? = null

    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    class Meta {

        @SerializedName("status")
        @Expose
        var status: Boolean? = null

        @SerializedName("message")
        @Expose
        var message: String? = null

        @SerializedName("message_code")
        @Expose
        var message_code: String? = null

        @SerializedName("status_code")
        @Expose
        var status_code: Int? = null
    }
}
