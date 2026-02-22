package com.savenko.track.shared.data.other.constants

import kotlin.math.pow
import kotlin.math.round

const val FIAT_SCALE = 2
const val CRYPTO_SCALE = 4
const val PERCENT_SCALE = 2

fun Double.toFormattedCurrency(scale: Int): String {
    val factor = 10.0.pow(scale)
    val rounded = round(this * factor) / factor
    return rounded.toString().let { s ->
        if (!s.contains('.')) s
        else s.trimEnd('0').trimEnd('.')
    }
}
