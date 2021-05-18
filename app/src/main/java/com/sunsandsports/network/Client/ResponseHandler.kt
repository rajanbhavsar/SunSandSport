package com.sunsandsports.network.Client


/**
 * This is the Sealed class to define the api call in three variation as
 * loading
 * fail
 * success
 * to define individual event on each api call for screen.
 */
sealed class ResponseHandler<out T> {
    object Loading : ResponseHandler<Nothing>()
    class OnFailed(val code: Int, val message: String, val messageCode: String) :
        ResponseHandler<Nothing>()

    class OnSuccessResponse<T>(val response: T) : ResponseHandler<T>()
}
