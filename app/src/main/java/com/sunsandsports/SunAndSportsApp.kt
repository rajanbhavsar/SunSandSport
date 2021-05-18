package com.sunsandsports

import android.app.Application
import com.sunsandsports.network.ApiClient

class SunAndSportsApp : Application() {

    /**
     * This is the Application level class to initalize the retrofit Object.
     *
     */
    override fun onCreate() {
        super.onCreate()
        ApiClient.initRetrofit()
    }
}