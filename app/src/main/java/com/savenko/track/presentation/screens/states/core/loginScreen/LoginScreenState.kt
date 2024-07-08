package com.savenko.track.presentation.screens.states.core.loginScreen

import com.savenko.track.domain.models.currency.Currency

data class LoginScreenState (
    val name : String,
    val budget : Float,
    val currency: Currency
)