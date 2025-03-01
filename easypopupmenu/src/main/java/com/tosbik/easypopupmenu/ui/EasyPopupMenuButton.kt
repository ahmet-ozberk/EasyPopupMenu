package com.tosbik.easypopupmenu.ui

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.tosbik.easypopupmenu.model.EasyPopupMenuItem
import com.tosbik.easypopupmenu.model.EasyPopupState
import com.tosbik.easypopupmenu.util.provider.EasyPopupPositionProvider

@Composable
internal fun EasyPopupMenuButton(
    popupState: EasyPopupState,
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: List<EasyPopupMenuItem>
) {
    val density = LocalDensity.current
    val expandedStates = remember { MutableTransitionState(false) }
    expandedStates.targetState = popupState.isVisible

    if (expandedStates.currentState || expandedStates.targetState) {
        val popupPositionProvider = EasyPopupPositionProvider(
            contentOffset = offset,
            density = density
        ) { alignment, isTop ->
            popupState.horizontalAlignment = alignment
            popupState.isTop = isTop
        }
        val transformOrigin = TransformOrigin(
            pivotFractionX = when (popupState.horizontalAlignment) {
                Alignment.Start -> 0f
                Alignment.CenterHorizontally -> 0.5f
                else -> 1f
            },
            pivotFractionY = if (popupState.isTop) 1f else 0f
        )

        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            properties = properties,
        ) {
            EasyPopupMenuContent(
                expandedStates = expandedStates,
                transformOrigin = transformOrigin,
                modifier = modifier,
                content = content
            )
        }
    }
}