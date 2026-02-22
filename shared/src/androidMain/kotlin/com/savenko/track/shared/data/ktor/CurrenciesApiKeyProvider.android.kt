package com.savenko.track.shared.data.ktor

import com.savenko.track.shared.BuildConfig

actual object CurrenciesApiKeyProvider {
    actual fun get(): String = BuildConfig.CURRENCIES_API_KEY
}
