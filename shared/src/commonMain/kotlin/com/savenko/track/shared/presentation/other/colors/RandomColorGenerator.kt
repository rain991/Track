package com.savenko.track.shared.presentation.other.colors

import kotlin.random.Random

private fun Int.toHex2(): String = toString(16).uppercase().padStart(2, '0')

fun generateRandomColor(): String {
    val alpha = 0xFF
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    return "0x${alpha.toHex2()}${red.toHex2()}${green.toHex2()}${blue.toHex2()}"
}

@Suppress("UNUSED")
fun convertColorFormat(color: String): String? {
    return if (color.startsWith("0x") && color.length == 10) {
        color.substring(4)
    }else{
        null
    }
}
