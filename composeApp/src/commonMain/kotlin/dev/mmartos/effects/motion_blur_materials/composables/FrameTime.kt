package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.withFrameNanos
import kotlinx.coroutines.isActive

/**
 * Represents timing information for a rendered frame.
 *
 * @property t Absolute time in seconds since an arbitrary but monotonic origin
 * (derived from [withFrameNanos]).
 * @property dt Delta time in seconds since the previous frame.
 */
data class FrameTime(
    val t: Float,
    val dt: Float,
)

/**
 * Remembers and produces a [FrameTime] state that updates once per frame.
 *
 * This composable uses [produceState] and [withFrameNanos] to track:
 * - The absolute frame time (`t`) in seconds
 * - The time delta (`dt`) between consecutive frames
 *
 * The state is updated continuously while the composable is in the composition
 * and the coroutine scope is active.
 *
 * This is useful for time-based animations, motion effects, or simulations
 * that require frame-accurate timing information.
 *
 * @param initialDt The initial delta time (in seconds) used before the first
 * frame timing can be calculated. Defaults to `1 / 60`.
 *
 * @return The current [FrameTime] containing absolute time and frame delta.
 */
@Composable
fun rememberFrameTimeState(
    initialDt: Float = 1f / 60f,
): FrameTime {
    val frame by produceState(initialValue = FrameTime(t = 0f, dt = initialDt)) {
        var last = 0L
        while (isActive) {
            withFrameNanos { now ->
                if (last == 0L) last = now
                val dt = ((now - last).coerceAtLeast(1L)) / 1_000_000_000f
                value = FrameTime(t = now / 1_000_000_000f, dt = dt)
                last = now
            }
        }
    }
    return frame
}
