package com.savenko.track.shared.data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val date: String? = null,
    val base: String? = null,
    val rates: Map<String, String> = emptyMap(),
)
