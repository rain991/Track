package com.savenko.track.shared.data.ktor

expect object CurrenciesApiKeyProvider {
    fun get(): String
}
