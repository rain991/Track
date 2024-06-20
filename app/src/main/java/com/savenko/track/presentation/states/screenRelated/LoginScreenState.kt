package com.savenko.track.presentation.states.screenRelated

import com.savenko.track.domain.models.currency.Currency

data class LoginScreenState (
    val name : String,
    val budget : Float,
    val currency: Currency
)