package com.sunsandsports.ui

import androidx.lifecycle.ViewModelProvider
import com.sunsandsports.R
import com.sunsandsports.base.FragmentBase
import com.sunsandsports.databinding.FragmentUserDetailBinding
import com.sunsandsports.viewmodel.ViewModelUserDetail

class FragmentDetail : FragmentBase<ViewModelUserDetail, FragmentUserDetailBinding>() {
    lateinit var viewmodel: ViewModelUserDetail

    override fun getLayoutId(): Int {
        return R.layout.fragment_user_detail
    }

    override fun setupToolbar() {
    }

    override fun initializeScreenVariables() {
        val userModel = arguments?.getParcelable<User>("userModel")!!
        getDataBinding().user = userModel

    }

    override fun getViewModel(): ViewModelUserDetail? {
        viewmodel = ViewModelProvider(this).get(ViewModelUserDetail::class.java)
        return viewmodel
    }
}