package dev.mmartos.effects.motion_blur_materials

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.toJsString
import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
fun main() {
    ComposeViewport {
        val assetType = URLSearchParams(window.location.search.toJsString()).get("asset")?.toAssetType()
        val key = assetType?.run { MotionBlurMaterialsKey.AssetKey(this) } ?: MotionBlurMaterialsKey.HomeKey
        App(initialKey = key)
    }
}

private fun String.toAssetType(): AssetType? = when (this) {
    "asset01" -> AssetType.ASSET01
    else -> null
}
