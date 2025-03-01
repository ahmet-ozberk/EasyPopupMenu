package com.tosbik.easypopupmenu

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.tosbik.easypopupmenu.model.EasyPopupMenuItem
import com.tosbik.easypopupmenu.model.EasyPopupState
import com.tosbik.easypopupmenu.theme.EasyPopupMenuTheme
import com.tosbik.easypopupmenu.theme.ProvideEasyPopupMenuTheme
import com.tosbik.easypopupmenu.ui.EasyPopupMenuButton


/**
 * Remember the state of the EasyPopupMenu
 * @param isVisible the initial visibility of the popup
 * @return the state of the EasyPopupMenu
 *
 * How to use:
 * ```
 * val state = rememberEasyPopupState(isVisible = false)
 *
 * EasyPopupMenu(
 *    state = state,
 *    items = listOf(
 *     EasyPopupMenuItem(
 *          text = "Item 1",
 *          onClick = {
 *              state.isVisible = false
 *          }
 *      ),
 *    ),
 * ) {
 *   // Content
 * }
 *
 */
@Composable
fun rememberEasyPopupState(isVisible: Boolean = false): EasyPopupState {
    val state by remember { mutableStateOf(EasyPopupState(isVisible)) }
    return state
}


/**
 * The state of the EasyPopupMenu
 * @sample EasyPopupMenuSample
 */

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun EasyPopupMenu(
    state: EasyPopupState = rememberEasyPopupState(false),
    onDismissRequest: () -> Unit = {},
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    theme: EasyPopupMenuTheme = EasyPopupMenuTheme.default(),
    items: List<EasyPopupMenuItem> = emptyList(),
    content: @Composable () -> Unit
) {
    ProvideEasyPopupMenuTheme(theme = theme) {
        Box {
            EasyPopupMenuButton(
                popupState = state,
                onDismissRequest = onDismissRequest,
                modifier = modifier,
                offset = offset,
                properties = properties,
                content = items
            )
            Box(modifier = Modifier.clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { state.isVisible = !state.isVisible }
            )) {
                content()
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EasyPopupMenuSample() {
    val state1 = rememberEasyPopupState(true)
    val state2 = rememberEasyPopupState(true)

    val selectedItem = remember { mutableStateOf<String?>(null) }

    val vegetables = listOf(
        "Carrot",
        "Broccoli",
        "Spinach",
        "Tomato",
        "Cucumber",
        "Bell Pepper"
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("EasyPopupMenu") },
                actions = {
                    EasyPopupMenu(
                        state = state1,
                        onDismissRequest = { state1.isVisible = false },
                        items = listOf(
                            EasyPopupMenuItem(
                                text = "Search",
                                onClick = { state1.isVisible = false },
                                trailing = {
                                    Icon(
                                        Icons.Rounded.Search,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            ),
                            EasyPopupMenuItem(
                                text = "Add",
                                onClick = { state1.isVisible = false },
                                trailing = {
                                    Icon(
                                        Icons.Rounded.Add,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            ),
                            EasyPopupMenuItem(
                                text = "Profile",
                                onClick = { state1.isVisible = false },
                                trailing = {
                                    Icon(
                                        Icons.Rounded.AccountCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            ),
                        )
                    ) { Icon(Icons.Default.MoreVert, contentDescription = "Actions") }
                }
            )
        }) { _ ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onBackground),
            contentAlignment = Alignment.Center
        ) {
            EasyPopupMenu(
                state = state2,
                onDismissRequest = { state2.isVisible = false },
                theme = EasyPopupMenuTheme.dark(),
                items = vegetables.map { vegetable ->
                    EasyPopupMenuItem(
                        text = vegetable,
                        onClick = {
                            state2.isVisible = false
                            selectedItem.value = vegetable
                        },
                        trailing = {
                            if (vegetable == selectedItem.value) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }
            ) { Text("Show Popup Menu", color = Color.White) }
        }
    }
}