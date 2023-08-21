package com.example.frogsocial

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class FrogApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}