package com.savenko.track.shared.data.ktor

import platform.Foundation.NSBundle

actual object CurrenciesApiKeyProvider {
    actual fun get(): String {
        val infoPlistApiKey = NSBundle.mainBundle
            .objectForInfoDictionaryKey("CURRENCIES_API_KEY")
            ?.toString()
            .orEmpty()
            .trim()

        if (infoPlistApiKey.isNotEmpty()) {
            return infoPlistApiKey
        }

        return GeneratedRuntimeConfig.CURRENCIES_API_KEY.trim()
    }
}
