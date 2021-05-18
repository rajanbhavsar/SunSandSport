package com.sunsandsports.ui

import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import com.appname.structure.base.ViewModelBase
import com.sunsandsports.R
import com.sunsandsports.base.FragmentBase
import com.sunsandsports.databinding.FragmentSplashBinding

class FragmentSplash : FragmentBase<ViewModelBase, FragmentSplashBinding>() {
    override fun onResume() {
        super.onResume()
        Handler().postDelayed(
            {
                checkForNavigation()
            },
            2000
        )
    }

    private fun checkForNavigation() {
        getBaseActivity()?.navigateToNextScreen(R.id.action_move_to_dashboard_splash)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun setupToolbar() {
    }

    override fun initializeScreenVariables() {
        getBaseActivity()?.supportActionBar?.hide()
    }

    override fun getViewModel(): ViewModelBase? {
        return ViewModelProvider(this).get(ViewModelBase::class.java)
    }
}
