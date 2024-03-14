package com.example.track.presentation.common.parser

import androidx.compose.ui.graphics.Color

fun parseColor(hexColor: String): Color {
    val hex = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
    return Color(android.graphics.Color.parseColor("#$hex"))
}