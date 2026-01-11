package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.mmartos.effects.motion_blur_materials.composables.MotionBlurAnimatedIllustrationConstants.CIRCLE_RADIUS
import dev.mmartos.effects.motion_blur_materials.composables.MotionBlurAnimatedIllustrationConstants.OFFSET_X
import dev.mmartos.effects.motion_blur_materials.composables.MotionBlurAnimatedIllustrationConstants.OFFSET_Y
import dev.mmartos.effects.motion_blur_materials.composables.MotionBlurAnimatedIllustrationConstants.SAMPLING_RATE
import kotlinx.coroutines.delay

/**
 * Animated illustration that explains how motion-blur is formed by accumulating samples over time.
 *
 * The animation cycles through a few scenes:
 * - A baseline scene with a moving circle.
 * - A "frame buffer" visualization that accumulates samples when the shutter is open.
 * - A final result showing the accumulated motion blur.
 *
 * This composable is self-contained and runs indefinitely once composed.
 *
 * @param modifier Modifier applied to the root container of the illustration.
 */
@Composable
fun MotionBlurAnimatedIllustration(
    modifier: Modifier = Modifier,
) {
    var step by remember {
        mutableStateOf(
            AnimationStep(
                effect = TransitionEffect.Nothing,
                content = SceneContent.Nothing
            )
        )
    }

    LaunchedEffect(Unit) {
        while (true) {
            step = AnimationStep(
                effect = TransitionEffect.Nothing,
                content = SceneContent.Nothing,
            )
            delay(500)

            step = AnimationStep(
                effect = TransitionEffect.FadeIn,
                content = SceneContent.MotionBlurExplained(
                    text = "The scene contains a moving circle. The frame-buffer shutter is closed.",
                    leftPanelTime = MotionBlurTime(
                        startTime = -8f,
                        endTime = 8f,
                        durationInMs = 16000,
                    ),
                    rightPanelTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = -4f,
                        durationInMs = 0,
                    ),
                    shutterState = ShutterState.Closed,
                ),
            )
            delay(4000)

            step = AnimationStep(
                effect = TransitionEffect.Nothing,
                content = SceneContent.MotionBlurExplained(
                    text = "The circle keeps moving, and the shutter has just opened.",
                    leftPanelTime = MotionBlurTime(
                        startTime = -8f,
                        endTime = 8f,
                        durationInMs = 16000,
                    ),
                    rightPanelTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                    shutterState = ShutterState.Opened,
                ),
            )
            delay(4000)

            step = AnimationStep(
                effect = TransitionEffect.Nothing,
                content = SceneContent.MotionBlurExplained(
                    text = "The frame buffer keeps accumulating light from the scene.",
                    leftPanelTime = MotionBlurTime(
                        startTime = -8f,
                        endTime = 8f,
                        durationInMs = 16000,
                    ),
                    rightPanelTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                    shutterState = ShutterState.Opened,
                ),
            )
            delay(4000)

            step = AnimationStep(
                effect = TransitionEffect.Nothing,
                content = SceneContent.MotionBlurExplained(
                    text = "The circle keeps moving, and the frame-buffer shutter closes.",
                    leftPanelTime = MotionBlurTime(
                        startTime = -8f,
                        endTime = 8f,
                        durationInMs = 16000,
                    ),
                    rightPanelTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                    shutterState = ShutterState.Closed,
                ),
            )
            delay(4000)

            step = AnimationStep(
                effect = TransitionEffect.FadeOut,
                content = SceneContent.MotionBlurExplained(
                    text = "The circle keeps moving, and the frame-buffer shutter closes.",
                    leftPanelTime = MotionBlurTime(
                        startTime = -8f,
                        endTime = 8f,
                        durationInMs = 16000,
                    ),
                    rightPanelTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                    shutterState = ShutterState.Closed,
                ),
            )
            delay(500)

            step = AnimationStep(
                effect = TransitionEffect.FadeIn,
                content = SceneContent.FinalResult(
                    motionBlurTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                ),
            )
            delay(8000)

            step = AnimationStep(
                effect = TransitionEffect.FadeOut,
                content = SceneContent.FinalResult(
                    motionBlurTime = MotionBlurTime(
                        startTime = -4f,
                        endTime = 4f,
                        durationInMs = 8000,
                    ),
                ),
            )
            delay(500)
        }
    }

    AnimationStepContent(step, modifier)
}

private sealed class TransitionEffect {
    data object FadeIn : TransitionEffect()
    data object Nothing : TransitionEffect()
    data object FadeOut : TransitionEffect()
}

private data class AnimationStep(
    val effect: TransitionEffect,
    val content: SceneContent,
)

private sealed class SceneContent {
    data object Nothing : SceneContent()

    data class MotionBlurExplained(
        val text: String,
        val leftPanelTime: MotionBlurTime,
        val rightPanelTime: MotionBlurTime,
        val shutterState: ShutterState,
    ) : SceneContent()

    data class FinalResult(
        val motionBlurTime: MotionBlurTime,
    ) : SceneContent()
}

private data class MotionBlurTime(
    val startTime: Float,
    val endTime: Float,
    val durationInMs: Long,
)

private sealed class ShutterState {
    data object Opened : ShutterState()
    data object Closed : ShutterState()
}

@Composable
private fun AnimationStepContent(
    step: AnimationStep,
    modifier: Modifier = Modifier,
) {
    val visible = step.effect != TransitionEffect.FadeOut && step.content != SceneContent.Nothing

    AnimatedVisibility(
        visible = visible,
        modifier = modifier.fillMaxSize(),
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        RenderSceneContent(
            content = step.content,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun RenderSceneContent(
    content: SceneContent,
    modifier: Modifier
) {
    when (content) {
        is SceneContent.Nothing -> Unit
        is SceneContent.MotionBlurExplained -> SceneMotionBlurExplained(content, modifier = modifier)
        is SceneContent.FinalResult -> SceneFinalResult(content.motionBlurTime, modifier = modifier)
    }
}

@Composable
private fun SceneMotionBlurExplained(
    sceneContent: SceneContent.MotionBlurExplained,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(32.dp, CenterVertically),
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Row(
            horizontalArrangement = spacedBy(32.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            LeftPanel(
                panelTime = sceneContent.leftPanelTime,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(13f / 9f)
            )
            RightPanel(
                panelTime = sceneContent.rightPanelTime,
                sceneContent.shutterState,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(13f / 9f)
            )
        }
        AnimatedContent(
            targetState = sceneContent.text,
            transitionSpec = {
                (fadeIn(animationSpec = tween(durationMillis = 250)))
                    .togetherWith(fadeOut(animationSpec = tween(durationMillis = 250)))
            },
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SceneFinalResult(
    motionBlurTime: MotionBlurTime,
    modifier: Modifier = Modifier,
) {
    PanelContainer(
        footerText = "Final Result",
        modifier = modifier
            .requiredSize(592.dp, 410.dp),
    ) {
        BlurredSceneAccumulation(
            startTime = motionBlurTime.startTime,
            targetTime = motionBlurTime.endTime,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun PanelContainer(
    footerText: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp),
        modifier = modifier
    ) {
        Text(text = footerText, style = MaterialTheme.typography.headlineMedium)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium,
                )
                .clip(MaterialTheme.shapes.medium),
        ) {
            content.invoke()
        }
    }
}

@Composable
private fun LeftPanel(
    panelTime: MotionBlurTime,
    modifier: Modifier = Modifier
) {
    val time = remember { Animatable(0f) }
    LaunchedEffect(panelTime) {
        time.snapTo(panelTime.startTime)
        time.animateTo(
            targetValue = panelTime.endTime,
            animationSpec = tween(durationMillis = panelTime.durationInMs.toInt(), easing = LinearEasing),
        )
    }
    PanelContainer(
        footerText = "Scene",
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
        ) {
            drawCircle(
                color = Color.Red,
                radius = CIRCLE_RADIUS.dp.toPx(),
                center = size.center + Offset(time.value * OFFSET_X.dp.toPx(), time.value * OFFSET_Y.dp.toPx()),
            )
        }
    }
}

@Composable
private fun RightPanel(
    panelTime: MotionBlurTime,
    shutterState: ShutterState,
    modifier: Modifier = Modifier,
) {
    val shutterProgress = remember { Animatable(0f) }
    LaunchedEffect(shutterState) {
        shutterProgress.animateTo(if (shutterState == ShutterState.Opened) 1f else 0f)
    }
    PanelContainer(
        footerText = "Frame Buffer",
        modifier = modifier,
    ) {
        BlurredSceneAnimated(
            motionBlurTime = panelTime,
            modifier = Modifier.fillMaxSize()
        )
        CameraShutter(
            bladeCount = 9,
            progress = shutterProgress.value,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun BlurredSceneAnimated(
    motionBlurTime: MotionBlurTime,
    modifier: Modifier = Modifier,
) {
    val time = remember { Animatable(0f) }
    LaunchedEffect(motionBlurTime) {
        time.snapTo(motionBlurTime.startTime)
        time.animateTo(
            targetValue = motionBlurTime.endTime,
            animationSpec = tween(durationMillis = motionBlurTime.durationInMs.toInt(), easing = LinearEasing),
        )
    }
    BlurredSceneAccumulation(
        startTime = motionBlurTime.startTime,
        targetTime = time.value,
        modifier = modifier
    )
}


@Composable
private fun BlurredSceneAccumulation(
    startTime: Float,
    targetTime: Float,
    modifier: Modifier = Modifier,
) {
    val frameBuffer = rememberGraphicsLayer()
    Canvas(modifier = modifier) {
        val radius = CIRCLE_RADIUS.dp.toPx()
        val offsetX = OFFSET_X.dp.toPx()
        val offsetY = OFFSET_Y.dp.toPx()
        frameBuffer.record {
            var curTime = startTime
            while (curTime < targetTime) {
                drawCircle(
                    color = Color.Red.copy(alpha = 1f / SAMPLING_RATE),
                    radius = radius,
                    center = size.center + Offset(curTime * offsetX, curTime * offsetY),
                )
                curTime += 1f / SAMPLING_RATE
            }
        }
        drawLayer(frameBuffer)
    }
}

private object MotionBlurAnimatedIllustrationConstants {
    const val CIRCLE_RADIUS = 24
    const val OFFSET_X = 32
    const val OFFSET_Y = -12
    const val SAMPLING_RATE = 60f
}
