package dev.mmartos.effects.motion_blur_materials

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Motion Blur Materials",
    ) {
        App()
    }
}