package com.example.muzyczka_projekt

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Muzyczka_projekt",
    ) {
        App()
    }
}