package com.savenko.track.shared.data.ktor

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val date: String,
    val base: String,
    val rates: Map<String, String>
)
