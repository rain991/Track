package com.savenko.track.data.retrofit

data class CurrencyResponse(
    val date: String,
    val base: String,
    val rates: Map<String, String>
)
