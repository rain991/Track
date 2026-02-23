package com.savenko.track.shared

import platform.Foundation.NSBundle

actual object AppInfo {
    actual val versionName: String =
        NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "unknown"
}
