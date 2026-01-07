package dev.mmartos.effects.motion_blur_materials.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp

/**
 * Represents a 1-dimensional color gradient defined by an ordered list of color stops.
 *
 * The gradient is sampled using a normalized value in the range `[0f, 1f]`,
 * where `0f` corresponds to the first color and `1f` corresponds to the last.
 * Intermediate values linearly interpolate between adjacent colors.
 *
 * This class is:
 * - Stateless after construction
 * - Deterministic
 * - Suitable for animations, shaders, and procedural effects
 *
 * @param inputColors An ordered, non-empty list of colors defining the gradient.
 *                    The order determines interpolation direction.
 * @throws IllegalArgumentException if `inputColors` is empty.
 */
class Gradient(
    inputColors: List<Color>,
) {

    private val gradient: List<Color>

    init {
        require(inputColors.isNotEmpty())
        gradient = inputColors
    }

    /**
     * Returns the interpolated color at the given normalized position.
     *
     * Values outside the `[0f, 1f]` range are clamped before sampling.
     *
     * @param factor Normalized position along the gradient.
     * @return The interpolated [Color] at the given position.
     */
    fun colorAt(factor: Float): Color {
        val clampedT = factor.coerceIn(0f, 1f)

        val scaled = clampedT * (gradient.size - 1)
        val index = scaled.toInt()
        val frac = scaled - index

        val c0 = gradient[index]
        val c1 = gradient[(index + 1).coerceAtMost(gradient.lastIndex)]

        return lerp(c0, c1, frac)
    }
}
