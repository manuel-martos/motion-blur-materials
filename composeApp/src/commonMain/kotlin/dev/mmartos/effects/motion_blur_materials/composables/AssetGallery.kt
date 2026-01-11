package dev.mmartos.effects.motion_blur_materials.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import dev.mmartos.effects.motion_blur_materials.AssetType
import kotlin.math.min

@Composable
fun AssetGallery(
    onAssetSelected: (AssetType) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = spacedBy(16.dp),
        verticalArrangement = spacedBy(16.dp),
        modifier = modifier,
    ) {
        items(AssetType.entries) { assetType ->
            AssetContainer(
                assetType = assetType,
                onAssetSelected = { onAssetSelected(assetType) },
            ) {
                when (assetType) {
                    AssetType.ASSET01 -> SimpleAnimation()
                    AssetType.ASSET02 -> MotionBlurAnimatedIllustration()
                }
            }
        }
    }
}

@Composable
private fun AssetContainer(
    assetType: AssetType,
    onAssetSelected: (AssetType) -> Unit,
    modifier: Modifier = Modifier,
    containerSize: IntSize = IntSize(1280, 720),
    content: @Composable () -> Unit,
) {

    OutlinedCard(
        onClick = { onAssetSelected(assetType) },
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(containerSize.width / containerSize.height.toFloat())
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            // Here, we are assuming that the ideal screen size is 1280x720
            val scale = min(maxWidth / containerSize.width.dp, maxHeight / containerSize.height.dp)
            Box(
                Modifier
                    .requiredSize(containerSize.width.dp, containerSize.height.dp)
                    .scale(scale)
            ) {
                content.invoke()
            }
        }
    }
}
