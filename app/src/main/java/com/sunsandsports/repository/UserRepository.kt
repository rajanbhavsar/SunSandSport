package com.sunsandsports.repository

import com.sunsandsports.base.BaseRepository
import com.sunsandsports.network.Client.ApiInterface
import com.sunsandsports.network.Client.ResponseHandler
import com.sunsandsports.network.model.ResponseListData
import com.sunsandsports.ui.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * This is the User Repository to define the Api call for Fetching the User Lst from given Api Url.
 */
class UserRepository(private val apiInterface: ApiInterface) : BaseRepository() {

    suspend fun callUserListApi(pageNumber: Int): ResponseHandler<ResponseListData<User>?> {
        return withContext(Dispatchers.Default) {
            return@withContext makeAPICallForList(
                call = {
                    apiInterface.getUserList("https://randomuser.me/api/?page=$pageNumber&results=10")
                })
        }
    }
}