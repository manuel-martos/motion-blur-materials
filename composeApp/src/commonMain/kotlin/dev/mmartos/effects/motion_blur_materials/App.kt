package dev.mmartos.effects.motion_blur_materials

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import dev.mmartos.effects.motion_blur_materials.MotionBlurMaterialsKey.AssetKey
import dev.mmartos.effects.motion_blur_materials.MotionBlurMaterialsKey.HomeKey
import dev.mmartos.effects.motion_blur_materials.screen.asset.AssetScreen
import dev.mmartos.effects.motion_blur_materials.screen.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed interface MotionBlurMaterialsKey {
    data object HomeKey : MotionBlurMaterialsKey
    data class AssetKey(val assetType: AssetType) : MotionBlurMaterialsKey
}

@Composable
@Preview
fun App(
    initialKey: MotionBlurMaterialsKey = HomeKey,
) {
    var currentKey by remember { mutableStateOf(initialKey) }
    MaterialTheme {
        Column(
            modifier = Modifier
                .background(brush = Brush.verticalGradient(colors = listOf(Color(0xffffffff), Color(0xffe0e0e0))))
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (val key = currentKey) {
                is HomeKey ->
                    HomeScreen(
                        onAssetSelected = { assetType -> currentKey = AssetKey(assetType) },
                    )

                is AssetKey ->
                    AssetScreen(
                        assetType = key.assetType,
                    )
            }
        }
    }
}
