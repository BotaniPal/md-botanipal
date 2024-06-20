package com.botanipal.botanipal.ui

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class BotaniPal : Application() {
        override fun onCreate() {
            super.onCreate()
            AndroidThreeTen.init(this)
        }
}