package com.sunsandsports.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunsandsports.R
import com.sunsandsports.adapter.UserListAdapter
import com.sunsandsports.base.ActivityBase
import com.sunsandsports.base.FragmentBase
import com.sunsandsports.databinding.FragmentHomeBinding
import com.sunsandsports.listener.clickListener
import com.sunsandsports.network.Client.ResponseHandler
import com.sunsandsports.network.model.ResponseListData
import com.sunsandsports.viewmodel.ViewModelHome

class FragmentHome : FragmentBase<ViewModelHome, FragmentHomeBinding>(), clickListener {

    private var pageNumber: Int = 1
    lateinit var viewmodel: ViewModelHome
    private val userArrayList = ArrayList<User>()
    var userListAdapter: UserListAdapter? = null
    var isLoading: Boolean = false
    var lastPosition = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLiveDatacallback()
    }

    private fun initLiveDatacallback() {
        viewmodel.responseLiveData.observe(
            this,
            Observer { state ->
                if (state == null) {
                    return@Observer
                }
                when (state) {
                    is ResponseHandler.Loading -> {
                        isLoading = true
                        viewmodel.showProgressBar(true)
                    }
                    is ResponseHandler.OnFailed -> {
                        isLoading = false
                        viewmodel.showProgressBar(false)
                        httpFailedHandler(state.code, state.message, state.messageCode)
                    }
                    is ResponseHandler.OnSuccessResponse<ResponseListData<User>?> -> {
                        isLoading = false
                        viewmodel.showProgressBar(false)
                        lastPosition = userArrayList.size - 1
                        if (state.response?.results.isNullOrEmpty().not()) {
                            if (pageNumber <= 1) {
                                userArrayList.clear()
                            }
                            userArrayList.addAll(state.response?.results!!)
                        }
                        pageNumber++
                        setRecycleView(lastPosition)
                    }
                }
            }
        )
    }

    private fun setRecycleView(lastPosition: Int) {
        if (userListAdapter == null) {
            getDataBinding().rvList.layoutManager = LinearLayoutManager(requireActivity())
            userListAdapter = UserListAdapter(requireActivity(), userArrayList, this)
            getDataBinding().rvList.adapter = userListAdapter
            getDataBinding().rvList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayout.VERTICAL
                )
            )

            getDataBinding().rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lastCompletelyVisibleItemPosition =
                        (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if (!isLoading && lastCompletelyVisibleItemPosition > userArrayList.size - 4) {
                        viewmodel.fetchUserList(pageNumber)
                    }
                }
            })
        } else {
            userListAdapter?.notifyItemRangeChanged(lastPosition, userArrayList.size)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBaseActivity()?.supportActionBar?.show()
    }

    override fun getViewModel(): ViewModelHome? {
        viewmodel = ViewModelProvider(this).get(ViewModelHome::class.java)
        return viewmodel
    }

    override fun setupToolbar() {
    }

    override fun initializeScreenVariables() {
        getDataBinding().rvList.layoutManager = LinearLayoutManager(requireActivity())
        userListAdapter = UserListAdapter(requireActivity(), userArrayList, this)
        getDataBinding().rvList.adapter = userListAdapter
        getDataBinding().rvList.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayout.VERTICAL
            )
        )

        getDataBinding().rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastCompletelyVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!isLoading && lastCompletelyVisibleItemPosition > userArrayList.size - 4) {
                    viewmodel.fetchUserList(pageNumber)
                }
            }
        })
    }

    fun callDetailPage(userModel: User) {
        val action = FragmentHomeDirections.actionMoveToDetail(userModel)
        (activity as ActivityBase<*>).navHostFragment.navController.navigate(action)
    }

    override fun onUserItemClick(model: User) {
        callDetailPage(model)
    }

}
