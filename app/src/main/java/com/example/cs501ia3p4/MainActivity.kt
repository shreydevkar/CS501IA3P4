package com.example.cs501ia3p4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ResponsiveScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResponsiveScreen() {

    val items = listOf("Home", "Profile", "Settings", "About")
    var selectedItem by remember { mutableStateOf("Home") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Responsive Layout") })
        }
    ) { innerPadding ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (maxWidth < 600.dp) {
                // Phone layout
                PhoneLayout(items = items, selectedItem = selectedItem) { selectedItem = it }
            } else {
                // Tablet layout
                TabletLayout(items = items, selectedItem = selectedItem) { selectedItem = it }
            }
        }
    }
}

/* ---------------- PHONE LAYOUT ---------------- */

@Composable
fun PhoneLayout(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Phone Layout",
            style = MaterialTheme.typography.headlineMedium
        )

        // Navigation list
        items.forEach { item ->
            ListItem(
                headlineContent = { Text(item) },
                leadingContent = {
                    Icon(
                        imageVector = when (item) {
                            "Home" -> Icons.Default.Home
                            "Profile" -> Icons.Default.Person
                            "Settings" -> Icons.Default.Settings
                            else -> Icons.Default.Info
                        },
                        contentDescription = null
                    )
                },
                supportingContent = {
                    if (item == selectedItem) {
                        Text("Selected")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            Divider()
        }

        // Detail content
        DetailContent(selectedItem)
    }
}

/* ---------------- TABLET LAYOUT ---------------- */

@Composable
fun TabletLayout(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {

        // Left pane: NavigationRail
        NavigationRail(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            items.forEach { item ->
                NavigationRailItem(
                    selected = selectedItem == item,
                    onClick = { onItemSelected(item) },
                    icon = {
                        Icon(
                            imageVector = when (item) {
                                "Home" -> Icons.Default.Home
                                "Profile" -> Icons.Default.Person
                                "Settings" -> Icons.Default.Settings
                                else -> Icons.Default.Info
                            },
                            contentDescription = null
                        )
                    },
                    label = { Text(item) }
                )
            }
        }

        // Right pane: detail content
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(24.dp)
        ) {
            DetailContent(selectedItem)
        }
    }
}

/* ---------------- DETAIL CONTENT ---------------- */

@Composable
fun DetailContent(selectedItem: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = selectedItem,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "This is the detail content area for $selectedItem."
            )
            Button(onClick = { }) {
                Text("Action Button")
            }
            OutlinedButton(onClick = { }) {
                Text("Secondary Action")
            }
        }
    }
}

/* ---------------- PREVIEWS ---------------- */

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewPhone() {
    MaterialTheme {
        ResponsiveScreen()
    }
}

@Preview(showBackground = true, widthDp = 800, heightDp = 600)
@Composable
fun PreviewTablet() {
    MaterialTheme {
        ResponsiveScreen()
    }
}