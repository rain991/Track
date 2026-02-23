package com.savenko.track.shared.presentation.other.colors

import androidx.compose.ui.graphics.Color

fun parseColor(hexColor: String): Color {
    val normalized = hexColor.removePrefix("#").removePrefix("0x").removePrefix("0X")
    val argb = when (normalized.length) {
        6 -> "FF$normalized"
        8 -> normalized
        else -> "FF000000"
    }
    val colorLong = argb.toLongOrNull(16) ?: 0xFF000000
    return Color(colorLong)
}
