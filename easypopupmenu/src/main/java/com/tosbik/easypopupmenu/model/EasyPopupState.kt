package com.tosbik.easypopupmenu.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


/**
 * The state of the EasyPopupMenu
 * How to use:
 *
 * val popupState = rememberEasyPopupState(isVisible = false)
 */
class EasyPopupState(isVisible: Boolean = false) {
    var horizontalAlignment: Alignment.Horizontal by mutableStateOf(Alignment.CenterHorizontally)
    var isTop: Boolean by mutableStateOf(false)
    var isVisible: Boolean by mutableStateOf(isVisible)
}