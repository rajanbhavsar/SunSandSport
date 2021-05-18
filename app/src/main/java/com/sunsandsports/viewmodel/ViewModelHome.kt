package com.sunsandsports.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appname.structure.base.ViewModelBase
import com.sunsandsports.network.ApiClient
import com.sunsandsports.network.Client.ResponseHandler
import com.sunsandsports.network.model.ResponseListData
import com.sunsandsports.repository.UserRepository
import com.sunsandsports.ui.User
import kotlinx.coroutines.launch

class ViewModelHome : ViewModelBase() {
    var userRepository = UserRepository(ApiClient.getApiInterface())
    val responseLiveData = MutableLiveData<ResponseHandler<ResponseListData<User>?>>()

    init {
        fetchUserList(1)
    }

    fun fetchUserList(pageNumber: Int) {
        viewModelScope.launch(coroutineContext) {
            responseLiveData.value = ResponseHandler.Loading
            responseLiveData.value = userRepository.callUserListApi(pageNumber)

        }
    }
}
