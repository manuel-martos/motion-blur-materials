package dev.mmartos.effects.motion_blur_materials.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mmartos.effects.motion_blur_materials.AssetType
import dev.mmartos.effects.motion_blur_materials.composables.AssetGallery

@Composable
fun HomeScreen(
    onAssetSelected: (AssetType) -> Unit,
    modifier: Modifier = Modifier,
) {
    AssetGallery(
        onAssetSelected = onAssetSelected,
        modifier = modifier
    )
}