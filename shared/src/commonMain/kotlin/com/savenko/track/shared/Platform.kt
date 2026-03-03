package com.savenko.track.shared

expect object Platform {
    val name: String
    val type : PlatformTarget
}

enum class PlatformTarget{
    Android, IOS
}
