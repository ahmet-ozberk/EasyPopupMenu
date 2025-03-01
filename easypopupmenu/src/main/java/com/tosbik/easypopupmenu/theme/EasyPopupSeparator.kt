package com.tosbik.easypopupmenu.theme


import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Represents the separator between the items in the EasyPopupMenu.
 *
 * @param color The color of the separator.
 * @param thickness The thickness of the separator.
 * @param content The content of the separator.
 *
 * Dark separator with default color and thickness.
 * @see [EasyPopupSeparator.dark]
 */
data class EasyPopupSeparator(
    val color: Color = Color(0xFFC6C6C8),
    val thickness: Dp = 0.6.dp,
    val content: (@Composable () -> Unit)? = {
        HorizontalDivider(
            color = color,
            thickness = thickness
        )
    }
) {
    companion object {
        val dark: EasyPopupSeparator = EasyPopupSeparator(
            color =  Color(0xFF020202),
            thickness = 0.6.dp
        )
    }
}