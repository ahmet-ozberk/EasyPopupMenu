package com.tosbik.easypopupmenu.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tosbik.easypopupmenu.model.EasyPopupMenuItem
import com.tosbik.easypopupmenu.theme.useEasyPopupMenuTheme
import com.tosbik.easypopupmenu.util.Constants

@Composable
internal fun EasyPopupMenuItemComponent(item: EasyPopupMenuItem, isLastItem: Boolean = false) {
    val theme = useEasyPopupMenuTheme()

    Column {
        Box(
            modifier = Modifier
                .clickable { item.onClick.invoke() }
                .background(item.backgroundColor ?: theme.backgroundColor)
                .padding(
                    horizontal = Constants.horizontalPadding,
                    vertical = Constants.verticalPadding
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                item.leading.let { leading ->
                    leading?.invoke()
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = item.text,
                    modifier = Modifier.weight(1f),
                    color = item.textColor ?: theme.textColor,
                    fontWeight = item.fontWeight ?: theme.fontWeight,
                    fontSize = item.textSize ?: theme.textSize
                )
                item.trailing.let { trailing ->
                    Spacer(modifier = Modifier.width(8.dp))
                    trailing?.invoke()
                }
            }
        }
        if (isLastItem.not()) {
            val separator = item.separator ?: theme.separator
            separator?.content?.invoke()
        }
    }
}