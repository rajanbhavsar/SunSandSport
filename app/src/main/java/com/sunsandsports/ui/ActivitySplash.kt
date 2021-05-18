package com.sunsandsports.ui

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.appname.structure.base.ViewModelBase
import com.sunsandsports.R
import com.sunsandsports.base.ActivityBase
import com.sunsandsports.databinding.ActivitySplashBinding

class ActivitySplash : ActivityBase<ViewModelBase>() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        setNavaigationHostFragment(supportFragmentManager.findFragmentById(R.id.splash_host) as NavHostFragment)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }


    fun displayProgress(t: Boolean) {
        Log.e("Hide Progress----", "-----Hide Progress")
        binding.loading = t
    }

}
