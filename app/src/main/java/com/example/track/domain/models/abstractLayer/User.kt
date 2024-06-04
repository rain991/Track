package com.example.track.domain.models.abstractLayer

import com.example.track.data.other.constants.BUDGET_DEFAULT
import com.example.track.data.other.constants.CURRENCY_FIAT
import com.example.track.data.other.constants.LOGIN_COUNT_DEFAULT
import com.example.track.data.other.constants.NAME_DEFAULT
import com.example.track.data.other.constants.NEEDS_LOGIN

data class User(
    var username: String = NAME_DEFAULT,
    var needsLogin: Boolean = NEEDS_LOGIN,
    var loginCount: Int = LOGIN_COUNT_DEFAULT,
    var budget: Float = BUDGET_DEFAULT,
    var currency: String = CURRENCY_FIAT.ticker
)
