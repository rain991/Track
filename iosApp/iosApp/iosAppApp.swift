//
//  iosAppApp.swift
//  iosApp
//
//  Created by Ivan on 20/02/2026.
//

import SwiftUI
import shared

@main
struct iosAppApp: App {
    init() {
        KoinInitializer().doInit()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
