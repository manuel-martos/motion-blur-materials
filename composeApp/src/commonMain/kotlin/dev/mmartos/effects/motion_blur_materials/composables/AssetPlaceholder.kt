package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.mmartos.effects.motion_blur_materials.AssetType
import kotlin.math.min

@Composable
fun AssetPlaceholder(
    assetType: AssetType,
    modifier: Modifier = Modifier,
    containerSize: IntSize = IntSize(1280, 720),
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(containerSize.width / containerSize.height.toFloat()),
    ) {
        // Here, we are assuming that the ideal screen size is 1280x720
        val scale = min(maxWidth / containerSize.width.dp, maxHeight / containerSize.height.dp)
        Box(
            Modifier
                .requiredSize(containerSize.width.dp, containerSize.height.dp)
                .scale(scale)
        ) {
            when (assetType) {
                AssetType.ASSET01 -> SimpleAnimation()
            }
        }
    }
}
