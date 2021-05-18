package com.sunsandsports.network.Client

import com.sunsandsports.network.model.ResponseListData
import com.sunsandsports.ui.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 * This is the Api level interface to define the Api with their relative response model.
 */
interface ApiInterface {

    @GET("")
    suspend fun getUserList(@Url url: String): Response<ResponseListData<User>>
}
