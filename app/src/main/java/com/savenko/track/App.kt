package com.savenko.track

import android.app.Application
import com.savenko.track.shared.KoinInitializer
import org.koin.core.component.KoinComponent

class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}