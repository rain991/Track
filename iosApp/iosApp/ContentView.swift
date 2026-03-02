//
//  ContentView.swift
//  iosApp
//
//  Created by Ivan on 20/02/2026.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        ComposeRootViewController()
            .ignoresSafeArea(.container, edges: .bottom)
    }
}

struct ComposeRootViewController: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

#Preview {
    ContentView()
}
