package com.tosbik.easypopupmenu.ui

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.tosbik.easypopupmenu.model.EasyPopupMenuItem
import com.tosbik.easypopupmenu.theme.useEasyPopupMenuTheme
import com.tosbik.easypopupmenu.util.extension.advancedShadow


@Composable
internal fun EasyPopupMenuContent(
    expandedStates: MutableTransitionState<Boolean>,
    transformOrigin: TransformOrigin,
    modifier: Modifier = Modifier,
    content: List<EasyPopupMenuItem>
) {
    val theme = useEasyPopupMenuTheme()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val transition = rememberTransition(expandedStates, "EasyPopupMenuContent")

    val scale by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = theme.dampingRatio,
                stiffness = theme.stiffness
            )
        },
        label = "Popup Scale"
    ) {
        if (it) {
            1f
        } else {
            0f
        }
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            spring(
                dampingRatio = theme.dampingRatio,
                stiffness = theme.stiffness
            )
        },
        label = "Popup Alpha"
    ) {
        if (it) {
            1f
        } else {
            0f
        }
    }

    fun GraphicsLayerScope.graphicsLayerAnim() {
        scaleX = scale
        scaleY = scale
        this.alpha = alpha
        this.clip = false
        this.transformOrigin = transformOrigin
        this.shape = RoundedCornerShape(theme.cardCornerRadius)
    }

    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .widthIn(min = theme.minWidth, max = theme.maxWidth)
            .heightIn(min = theme.minHeight, max = screenHeight * theme.maxHeightFactor)
            .graphicsLayer {
                graphicsLayerAnim()
            }
            .advancedShadow(
                color = theme.elevationColor,
                alpha = theme.elevationAlpha,
                cornersRadius = theme.cardCornerRadius,
                shadowBlurRadius = theme.cardElevation.dp,
                offsetY = theme.elevationOffsetY
            )
            .clip(RoundedCornerShape(theme.cardCornerRadius))

            .background(theme.backgroundColor)
    ) {
        Column(
            modifier = modifier
                .width(IntrinsicSize.Max)
                .verticalScroll(rememberScrollState()),
        ) {
            content.forEach {
                EasyPopupMenuItemComponent(it, isLastItem = it == content.last())
            }
        }
    }
}