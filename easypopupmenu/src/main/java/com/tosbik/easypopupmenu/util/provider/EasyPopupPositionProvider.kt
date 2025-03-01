package com.tosbik.easypopupmenu.util.provider

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import kotlin.math.abs

internal data class EasyPopupPositionProvider(
    private val contentOffset: DpOffset,
    private val density: Density,
    private val onPopupPositionFound: (Alignment.Horizontal, Boolean) -> Unit
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val contentOffsetX = with(density) { contentOffset.x.roundToPx() }
        val contentOffsetY = with(density) { contentOffset.y.roundToPx() }

        // Horizontal position calculation
        val (horizontalAlignment, xOffset) = calculateHorizontalPosition(
            anchorBounds = anchorBounds,
            windowSize = windowSize,
            layoutDirection = layoutDirection,
            popupContentSize = popupContentSize,
            contentOffsetX = contentOffsetX
        )

        // Vertical position calculation
        val (yOffset, isPlacedAbove) = calculateVerticalPosition(
            anchorBounds = anchorBounds,
            windowSize = windowSize,
            popupContentSize = popupContentSize,
            contentOffsetY = contentOffsetY
        )

        onPopupPositionFound(horizontalAlignment, isPlacedAbove)
        return IntOffset(xOffset, yOffset)
    }

    private fun calculateHorizontalPosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize,
        contentOffsetX: Int
    ): Pair<Alignment.Horizontal, Int> {
        val anchorWidth = anchorBounds.right - anchorBounds.left
        val popupWidth = popupContentSize.width

        val anchorCenter = anchorBounds.left + anchorWidth / 2
        val screenCenter = windowSize.width / 2

        val centerOffset = anchorCenter - popupWidth / 2

        val centerThreshold = windowSize.width * 0.2f
        val isButtonCentered = abs(anchorCenter - screenCenter) < centerThreshold

        val wouldFitCentered = centerOffset >= 0 && (centerOffset + popupWidth) <= windowSize.width

        if (isButtonCentered && wouldFitCentered) {
            return Alignment.CenterHorizontally to centerOffset
        }

        return when (layoutDirection) {
            LayoutDirection.Ltr -> {
                val start = anchorBounds.left + contentOffsetX
                val end = anchorBounds.right - contentOffsetX - popupWidth
                val center = anchorBounds.left + (anchorWidth - popupWidth) / 2

                when {
                    start + popupWidth <= windowSize.width -> Alignment.Start to start
                    end >= 0 -> Alignment.End to end
                    center + popupWidth / 2 <= windowSize.width && center >= 0 ->
                        Alignment.CenterHorizontally to center
                    else -> Alignment.Start to maxOf(0, minOf(start, windowSize.width - popupWidth))
                }
            }
            LayoutDirection.Rtl -> {
                val start = anchorBounds.right - contentOffsetX - popupWidth
                val end = anchorBounds.left + contentOffsetX
                val center = anchorBounds.left + (anchorWidth - popupWidth) / 2

                when {
                    start >= 0 -> Alignment.Start to start
                    end + popupWidth <= windowSize.width -> Alignment.End to end
                    center + popupWidth / 2 <= windowSize.width && center >= 0 ->
                        Alignment.CenterHorizontally to center
                    else -> Alignment.Start to maxOf(0, minOf(start, windowSize.width - popupWidth))
                }
            }
        }
    }

    private fun calculateVerticalPosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        popupContentSize: IntSize,
        contentOffsetY: Int
    ): Pair<Int, Boolean> {
        val spaceAbove = anchorBounds.top - contentOffsetY
        val spaceBelow = windowSize.height - anchorBounds.bottom - contentOffsetY

        return when {
            // Try to place below first if there's enough space
            popupContentSize.height <= spaceBelow -> {
                anchorBounds.bottom + contentOffsetY to false
            }
            // Then try to place above
            popupContentSize.height <= spaceAbove -> {
                (anchorBounds.top - popupContentSize.height - contentOffsetY) to true
            }
            // If neither fits, place where there's more space
            else -> {
                if (spaceAbove > spaceBelow) {
                    maxOf(0, anchorBounds.top - popupContentSize.height - contentOffsetY) to true
                } else {
                    minOf(windowSize.height - popupContentSize.height, anchorBounds.bottom + contentOffsetY) to false
                }
            }
        }
    }
}