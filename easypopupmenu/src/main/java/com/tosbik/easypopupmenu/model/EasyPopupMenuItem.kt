package com.tosbik.easypopupmenu.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.tosbik.easypopupmenu.theme.EasyPopupSeparator


/**
 * Represents a single item in the EasyPopupMenu
 * @param text the text of the item
 * @param leading the leading icon of the item
 * @param trailing the trailing icon of the item
 * @param onClick the action to be executed when the item is clicked
 * @param backgroundColor the background color of the item
 * @param textColor the text color of the item
 * @param textSize the text size of the item
 * @param fontWeight the font weight of the item
 * @param separator the separator of the item
 */
data class EasyPopupMenuItem(
    val text: String,
    val leading: (@Composable () -> Unit)? = null,
    val trailing: (@Composable () -> Unit)? = null,
    val onClick: () -> Unit = {},
    val backgroundColor: Color? = null,
    val textColor: Color? = null,
    val textSize: TextUnit? = null,
    val fontWeight: FontWeight? = null,
    val separator: EasyPopupSeparator? = null
)