package com.tosbik.easypopupmenu.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Theme class you can use for EasyPopupMenu.
 * @param minWidth Minimum width of the popup.
 * @param minHeight Minimum height of the popup.
 * @param maxWidth Maximum width of the popup.
 * @param maxHeightFactor Maximum height factor of the popup.
 * @param cardCornerRadius Corner radius of the popup.
 * @param verticalPadding Vertical padding of the popup.
 * @param horizontalPadding Horizontal padding of the popup.
 * @param textSize Text size of the popup.
 * @param fontWeight Font weight of the popup.
 * @param backgroundColor Background color of the popup.
 * @param textColor Text color of the popup.
 * @param separator Separator of the popup [EasyPopupSeparator].
 * @param elevationColor Elevation color of the popup.
 * @param cardElevation Elevation of the popup.
 * @param elevationAlpha Alpha value of the elevation.
 * @param elevationOffsetY Offset of the elevation.
 * @param dampingRatio Damping ratio of the popup.
 * @param stiffness Stiffness of the popup.
 *
 * Default theme uses light colors.
 * @see [EasyPopupMenuTheme.default]
 *
 * Dark theme uses dark colors.
 * @see [EasyPopupMenuTheme.dark]
 */
@Immutable
data class EasyPopupMenuTheme(
    val minWidth: Dp = 180.dp,
    val minHeight: Dp = 32.dp,
    val maxWidth: Dp = 256.dp,
    val maxHeightFactor: Float = 0.6f,
    val cardCornerRadius: Dp = 8.dp,
    val verticalPadding: Dp = 8.dp,
    val horizontalPadding: Dp = 12.dp,

    val textSize: TextUnit = 14.sp,
    val fontWeight: FontWeight = FontWeight.Normal,

    val backgroundColor: Color = Color(0xFFF8F9FA),
    val textColor: Color = Color(0xFF000000),

    val separator: EasyPopupSeparator? = EasyPopupSeparator(),

    val elevationColor: Color = Color(0xFF000000),
    val cardElevation: Float = 12f,
    val elevationAlpha: Float = 0.1f,
    val elevationOffsetY: Dp = 4.dp,

    val dampingRatio: Float = 0.75f,
    val stiffness: Float = 400f
) {
    companion object {
        fun default() = EasyPopupMenuTheme()

        fun dark() = EasyPopupMenuTheme(
            backgroundColor = Color(0xFF202020),
            textColor = Color(0xFFFFFFFF),
            separator = EasyPopupSeparator.dark,
            elevationColor = Color(0xFF000000)
        )
    }
}

@Composable
fun ProvideEasyPopupMenuTheme(
    theme: EasyPopupMenuTheme = EasyPopupMenuTheme.default(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalEasyPopupMenuTheme provides theme
    ) {
        content()
    }
}

private val LocalEasyPopupMenuTheme = compositionLocalOf { EasyPopupMenuTheme.default() }


/**
 * Get the current EasyPopupMenuTheme.
 */
@Composable
fun useEasyPopupMenuTheme(): EasyPopupMenuTheme = LocalEasyPopupMenuTheme.current