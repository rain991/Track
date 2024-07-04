package com.savenko.track.presentation.other.colors

import kotlin.random.Random

fun generateRandomColor(): String {
    val alpha = 0xFF
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    return String.format("0x%02X%02X%02X%02X", alpha, red, green, blue)
}

fun convertColorFormat(color: String): String? {
    return if (color.startsWith("0x") && color.length == 10) {
        color.substring(4)
    }else{
        null
    }
}
