package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.tan

/**
 * Draws a camera-shutter-like shape composed of multiple rotating blades.
 *
 * The shutter opens and closes based on [progress], simulating the motion
 * of a real camera aperture. The number of blades determines the visual
 * smoothness and symmetry of the shutter.
 *
 * @param bladeCount Number of shutter blades. Must be at least 4.
 * @param progress Animation progress in the range `[0f, 1f]`,
 * where `0f` represents a fully closed shutter and `1f` a fully open one.
 * Values outside this range are coerced.
 * @param modifier Optional [Modifier] applied to the underlying [Canvas].
 *
 * @throws IllegalArgumentException if [bladeCount] is less than 4.
 */
@Composable
fun CameraShutter(
    bladeCount: Int,
    progress: Float,
    modifier: Modifier = Modifier,
) {
    require(bladeCount >= 4) { "bladeCount should be >= 4" }

    Canvas(
        modifier = modifier
            .clipToBounds()
    ) {
        drawShutter(
            progress = progress.coerceIn(0f, 1f),
            blades = bladeCount,
        )
    }
}

/**
 * Draws the shutter blades inside the current [DrawScope].
 *
 * Each blade is defined as a triangular [Path] and rendered repeatedly,
 * rotating around the canvas center. The blades are translated inward or
 * outward based on [progress], creating the shutter opening/closing effect.
 *
 * @param progress Normalized animation progress in the range `[0f, 1f]`.
 * @param blades Number of shutter blades to draw.
 */
private fun DrawScope.drawShutter(
    progress: Float,
    blades: Int,
) {
    val minDim = size.minDimension
    val bladeTan = tan(2.0 * PI / blades).toFloat()
    val shiftTan = tan(PI / 2.0 - PI / blades).toFloat()

    val bladePath = Path().apply {
        moveTo(0f, 0f)
        lineTo(minDim, 0f)
        lineTo(minDim, minDim * bladeTan)
        close()
    }

    val strokeWidth = 2.dp.toPx()

    translate(left = size.center.x, top = size.center.y) {
        val step = 360f / blades
        val dx = progress * minDim / shiftTan
        val dy = -progress * minDim

        for (i in 0 until blades) {
            rotate(degrees = step * i + step * 0.25f, pivot = Offset.Zero) {
                translate(left = dx, top = dy) {
                    drawPath(
                        path = bladePath,
                        color = Color(0x0ffefefef),
                        style = Fill
                    )
                    drawPath(
                        path = bladePath,
                        color = Color.Black,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
        }
    }
}
