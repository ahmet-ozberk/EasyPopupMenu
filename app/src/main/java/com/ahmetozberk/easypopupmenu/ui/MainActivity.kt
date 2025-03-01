package com.ahmetozberk.easypopupmenu.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ahmetozberk.easypopupmenu.ui.theme.MyappTheme
import com.tosbik.easypopupmenu.EasyPopupMenu
import com.tosbik.easypopupmenu.model.EasyPopupMenuItem
import com.tosbik.easypopupmenu.rememberEasyPopupState
import com.tosbik.easypopupmenu.theme.EasyPopupMenuTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyappTheme(darkTheme = false) {
                ExampleScreen()
            }
        }
    }
}

data class Vegetable(
    val id: Int,
    val name: String,
    val calories: Int,
    val color: Color
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExampleScreen() {
    val state1 = rememberEasyPopupState(true)
    val state2 = rememberEasyPopupState(true)

    val selectedItem = remember { mutableStateOf<Vegetable?>(null) }

    val vegetables = listOf(
        Vegetable(1, "Carrot", 41, Color.Red),
        Vegetable(2, "Broccoli", 55, Color.Green.copy(alpha = .8f)),
        Vegetable(3, "Spinach", 23, Color.Green),
        Vegetable(4, "Tomato", 22, Color.Red),
        Vegetable(5, "Cucumber", 8, Color.Green),
        Vegetable(6, "Bell Pepper", 31, Color.Yellow),
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
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground), contentAlignment = Alignment.Center) {
            EasyPopupMenu(
                state = state2,
                onDismissRequest = { state2.isVisible = false },
                theme = EasyPopupMenuTheme.dark(),
                items = vegetables.map { vegetable ->
                    EasyPopupMenuItem(
                        text = vegetable.name,
                        onClick = {
                            state2.isVisible = false
                            selectedItem.value = vegetable
                        },
                        leading = {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(18.dp)
                                    .background(color = vegetable.color)
                            )
                        },
                        trailing = {
                            if (vegetable.id == selectedItem.value?.id) {
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