package dev.mmartos.effects.motion_blur_materials.screen.asset

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.mmartos.effects.motion_blur_materials.AssetType
import dev.mmartos.effects.motion_blur_materials.composables.AssetPlaceholder

@Composable
fun AssetScreen(
    assetType: AssetType,
    modifier: Modifier = Modifier,
) {
    AssetPlaceholder(assetType, modifier)
}