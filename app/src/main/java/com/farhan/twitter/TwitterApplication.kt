package com.farhan.twitter

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Mohd Farhan on 5/7/2021.
 */
@HiltAndroidApp
class TwitterApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}