package com.savenko.track.shared.data.ktor

import platform.Foundation.NSBundle

actual object CurrenciesApiKeyProvider {
    actual fun get(): String {
        val raw = NSBundle.mainBundle.objectForInfoDictionaryKey("CURRENCIES_API_KEY") as? String
        return raw.orEmpty()
    }
}
