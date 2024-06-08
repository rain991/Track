package com.example.track.presentation.states.screenRelated

import com.example.track.domain.models.currency.Currency

data class LoginScreenState (
    val name : String,
    val budget : Float,
    val currency: Currency
)