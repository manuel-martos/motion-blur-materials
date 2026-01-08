package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import dev.mmartos.effects.motion_blur_materials.composables.SimpleAnimationConstants.NUM_CIRCLES
import dev.mmartos.effects.motion_blur_materials.composables.SimpleAnimationConstants.SPEED
import dev.mmartos.effects.motion_blur_materials.composables.SimpleAnimationConstants.TAU
import dev.mmartos.effects.motion_blur_materials.utils.Gradient
import dev.mmartos.effects.motion_blur_materials.utils.randomFloatFromSeed
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws a simple animated orbital system of colored circles.
 *
 * This composable renders a fixed number of dots that orbit around the
 * center of the available canvas area. Each dot follows a smooth,
 * pseudo-randomized trajectory based on deterministic offsets and
 * frame time, producing an organic motion effect.
 *
 * @param modifier Modifier used to adjust the layout or drawing behavior
 * of the underlying [Canvas].
 */
@Composable
fun SimpleAnimation(
    modifier: Modifier = Modifier
        .fillMaxSize(),
) {
    val frameTime = rememberFrameTimeState()
    val gradient = remember {
        Gradient(
            listOf(
                Color(0xFFF2A1AE),
                Color(0xFFF4BE82),
                Color(0xFFF2E37A),
                Color(0xFF8EDBC4),
                Color(0xFF9CBFE0),
                Color(0xFFB9AEDD),
            )
        )
    }
    val offsets = remember { List(NUM_CIRCLES) { getOffset(it) } }

    Canvas(
        modifier = modifier
    ) {
        val dotRadius = 16.dp.toPx()
        val orbitPadding = 16.dp.toPx()
        val orbitRadius = (0.5f * size.minDimension - orbitPadding).coerceAtLeast(0f)

        translate(left = size.center.x, top = size.center.y) {
            for (i in 0 until NUM_CIRCLES) {
                val factor = i / NUM_CIRCLES.toFloat()
                drawCircle(
                    color = gradient.colorAt(factor),
                    radius = dotRadius,
                    center = getPosition(frameTime, orbitRadius, offsets[i])
                )
            }
        }
    }
}

/**
 * Generates a deterministic angular offset for a given circle index.
 *
 * The offset is composed of multiple seeded random values combined
 * with different weights to produce smooth, non-repeating motion.
 *
 * @param idx Index of the circle.
 * @return An [Offset] containing angular phase values for x and y.
 */
private fun getOffset(idx: Int): Offset =
    Offset(
        x = TAU * (0.5f * randomFloatFromSeed(idx + 5L) +
                0.33f * randomFloatFromSeed(idx + 3L) +
                0.17f * randomFloatFromSeed(idx + 1L)),
        y = TAU * (0.5f * randomFloatFromSeed(idx + 6L) +
                0.33f * randomFloatFromSeed(idx + 4L) +
                0.17f * randomFloatFromSeed(idx + 2L)),
    )

/**
 * Computes the current position of a circle along its orbit.
 *
 * The position is calculated using trigonometric functions driven by
 * frame time, a global speed factor, and a per-circle phase offset.
 * Additional low-frequency modulation adds subtle wobble to the orbit.
 *
 * @param frameTime Current animation frame time.
 * @param orbitRadius Base radius of the orbit.
 * @param offset Phase offset unique to each circle.
 * @return The computed [Offset] representing the circle's position.
 */
private fun getPosition(frameTime: FrameTime, orbitRadius: Float, offset: Offset): Offset =
    Offset(
        x = orbitRadius * 0.9f * cos(SPEED * frameTime.t + offset.x + 1.552f * sin(0.25f * frameTime.t)),
        y = orbitRadius * 0.9f * sin(SPEED * frameTime.t + offset.y + 1.552f * cos(0.25f * frameTime.t)),
    )

private object SimpleAnimationConstants {
    const val NUM_CIRCLES = 16
    const val SPEED = 5f
    const val TAU = 2F * PI.toFloat()
}