package com.touchtalent.bobblesdk.demo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.touchtalent.bobble.core.BobbleSDK

class SampleDemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        // Initialise BobbleSdk and all its module
        BobbleSDK.initialise(this)
    }
}