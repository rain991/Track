package com.example.track.data.models.other

import com.example.track.data.constants.BUDGET_DEFAULT
import com.example.track.data.constants.CURRENCY_DEFAULT
import com.example.track.data.constants.LOGIN_COUNT_DEFAULT
import com.example.track.data.constants.NAME_DEFAULT
import com.example.track.data.constants.NEEDS_LOGIN

data class User(
    var username: String = NAME_DEFAULT,
    var needsLogin: Boolean = NEEDS_LOGIN,
    var loginCount: Int = LOGIN_COUNT_DEFAULT,
    var budget: Int = BUDGET_DEFAULT,
    var currency: String = CURRENCY_DEFAULT.ticker
)