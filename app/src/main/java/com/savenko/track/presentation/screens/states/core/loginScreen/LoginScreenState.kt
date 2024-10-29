package com.savenko.track.presentation.screens.states.core.loginScreen

import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.screens.core.LoginScreen

/**
 * Login screen state
 *
 * @property name state of user's name preference
 * @property budget state of user's budget value
 * @property currency state of user's budget currency preference
 * @constructor Create [LoginScreen] state
 */
data class LoginScreenState (
    val name : String,
    val budget : Float,
    val currency: Currency
)