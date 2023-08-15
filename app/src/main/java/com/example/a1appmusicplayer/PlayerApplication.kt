package com.example.a1appmusicplayer

import android.app.Application
import com.intuit.sdp.BuildConfig
import timber.log.Timber

class PlayerApplication: Application() {
    override fun onCreate() {
        super.onCreate()


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}