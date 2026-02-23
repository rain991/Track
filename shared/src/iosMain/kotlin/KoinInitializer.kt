package com.savenko.track.shared

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(listOf(settingsModule, appModule, databaseModule, coreModule, domainModule, viewModelModule))
        }
    }
}
