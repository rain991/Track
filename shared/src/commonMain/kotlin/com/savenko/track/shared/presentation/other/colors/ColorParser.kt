package com.savenko.track.shared.presentation.other.colors

import androidx.compose.ui.graphics.Color

fun parseColor(hexColor: String): Color {
    val normalized = hexColor.removePrefix("#").removePrefix("0x").removePrefix("0X")
    val argb = when (normalized.length) {
        6 -> "FF$normalized"
        8 -> normalized
        else -> "FF000000"
    }
    return Color(argb.toULong(16))
}
