package com.sunsandsports.base

import android.accounts.NetworkErrorException
import com.basekotlin.network.model.ErrorWrapper
import com.google.gson.Gson
import com.kotlinusermodule.network.model.HttpErrorCode
import com.sunsandsports.network.Client.HttpCommonMethod
import com.sunsandsports.network.Client.ResponseHandler
import com.sunsandsports.network.model.ResponseListData
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * This is the Base repository Class where we are defining the Api call Mechanism.
 * Here we have currently defined the Api call that returns the list of User.
 * Here we have also defined the Other Methods which will be executing based on the response code status code.
 *
 *
 */
open class BaseRepository {

    /**
     * This method will be called when we have response code that match with UnAuthorized Code.
     */
    fun failureResponseUnAuthorized(errorBody: ResponseBody?): ResponseHandler.OnFailed {
        val message: String
        val bodyString = errorBody?.string()
        val responseWrapper =
            Gson().fromJson<ErrorWrapper>(bodyString, ErrorWrapper::class.java)
        message = if (responseWrapper.meta?.status_code == 401) {
            if (responseWrapper.errors != null) {
                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
            } else {
                responseWrapper.meta?.message.toString()
            }
        } else {
            responseWrapper.meta?.message.toString()
        }
        return ResponseHandler.OnFailed(
            HttpErrorCode.UNAUTHORIZED.code,
            message,
            responseWrapper.meta?.message_code.toString()
        )
    }

    /**
     * This method will be executing when we have the Empty response code.
     */
    fun failureResponseEmptyResponse(errorBody: ResponseBody?): ResponseHandler.OnFailed {
        val message: String
        val bodyString = errorBody?.string()
        val responseWrapper =
            Gson().fromJson<ErrorWrapper>(bodyString, ErrorWrapper::class.java)
        message = if (responseWrapper.meta?.status_code == 422) {
            if (responseWrapper.errors != null) {
                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
            } else {
                responseWrapper.meta?.message.toString()
            }
        } else {
            responseWrapper.meta?.message.toString()
        }

        if (message.isEmpty()) {
            return ResponseHandler.OnFailed(
                HttpErrorCode.EMPTY_RESPONSE.code,
                message,
                responseWrapper.meta?.message_code.toString()
            )
        } else {
            return ResponseHandler.OnFailed(
                HttpErrorCode.NOT_DEFINED.code,
                message,
                responseWrapper.meta?.message_code.toString()
            )
        }
    }

    /**
     * This method will be used when we have Internet Connection or host Exception.
     */
    fun hasExceptionOfNetwork(e: Exception): Boolean {
        return (
                e is UnknownHostException || e is IOException ||
                        e is NetworkErrorException ||
                        e is ConnectionShutdownException
                )
    }

    /**
     * This is the Generic Api call Method here we are calling the Request and get the response body.
     * After receving the responsebody we are trying to evaluate the response code to manage the Response.
     * Let Say Response code 200 is used for Success Response/
     * Response code 401 will use for the UnAuthorized.
     */
    suspend fun <T : Any> makeAPICallForList(call: suspend () -> Response<ResponseListData<T>>): ResponseHandler<ResponseListData<T>?> {
        try {
            val response = call.invoke()
            when {
                response.code() == 200 -> return ResponseHandler.OnSuccessResponse(response.body())
                response.code() == 401 -> return failureResponseUnAuthorized(response.errorBody())
                else -> return failureResponseEmptyResponse(response.errorBody())
            }
        } catch (e: Exception) {
            return if (e is SocketTimeoutException ||
                hasExceptionOfNetwork(e)
            ) {
                ResponseHandler.OnFailed(HttpErrorCode.NO_CONNECTION.code, "", "")
            } else {
                ResponseHandler.OnFailed(HttpErrorCode.NOT_DEFINED.code, "", "")
            }
        }
    }
}
