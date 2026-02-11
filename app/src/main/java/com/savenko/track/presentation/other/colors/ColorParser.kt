package com.savenko.track.presentation.other.colors

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun parseColor(hexColor: String): Color {
    val hex = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
    return Color("#$hex".toColorInt())
}