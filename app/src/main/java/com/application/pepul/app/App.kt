package com.application.pepul.app

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication(){
    companion object {
        lateinit var instance: App
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)

    }

}