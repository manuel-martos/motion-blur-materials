package dev.mmartos.effects.motion_blur_materials

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform