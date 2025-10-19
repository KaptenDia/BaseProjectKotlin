package com.vascomm.basekotlin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Core application class for Base Structure Project
 * This is the entry point of the application
 */
@HiltAndroidApp
class BaseStructureApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize app components here
        // TODO: Initialize Firebase when google-services.json is added
        // FirebaseHelper.initialize(this)
    }
}

