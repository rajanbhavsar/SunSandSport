package com.sunsandsports.network.model

import com.appname.structure.network.model.ResponseWrapper
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This is the Generic Base Response Model class, on basis we are managing the list response.
 */
class ResponseListData<T> : ResponseWrapper<T>() {

    @SerializedName("results")
    @Expose
    var results: List<T>? = null

    override fun toString(): String {
        return "ResponseWrapper{" +
                "data=" + results.toString()
    }
}
