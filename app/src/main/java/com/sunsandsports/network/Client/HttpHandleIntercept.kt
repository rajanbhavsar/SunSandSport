package com.sunsandsports.network.Client

import com.sunsandsports.network.ApiClient
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This is the Interceptoor Level Class to print Api and response and manage the Api request.
 */
class HttpHandleIntercept : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().headers(getJsonHeader()).build()
        val response: Response?

        response = chain.proceed(request)
        if (response.code == 401) {
            return ApiClient.generateCustomResponse(
                401, "",
                chain.request()
            )!!
        } else if (response.code == 500) {
            return ApiClient.generateCustomResponse(
                500, "",
                chain.request()
            )!!
        }

        return response
    }

    private fun getJsonHeader(): Headers {
        val builder = Headers.Builder()
        builder.add("Content-Type", "application/json")
        builder.add("Accept", "application/json")
        return builder.build()
    }
}
