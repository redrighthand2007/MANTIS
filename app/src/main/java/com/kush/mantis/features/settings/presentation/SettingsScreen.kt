package com.kush.mantis.features.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.core.ui.components.TopHeader
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val hapticFeedback by viewModel.hapticFeedback.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopHeader(title = "Settings")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Profile Card (25% left, 75% right)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .weight(0.25f)
                            .aspectRatio(1f)
                            .clip(CircleShape)
                            .background(MantisGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Person, contentDescription = "Profile", tint = androidx.compose.ui.graphics.Color.Black, modifier = Modifier.size(40.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier.weight(0.75f)
                    ) {
                        Text(text = "Kush", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text(text = "Developer", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Haptics
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Haptic Feedback", fontSize = 18.sp)
                    Switch(checked = hapticFeedback, onCheckedChange = { viewModel.setHapticFeedback(it) }, colors = SwitchDefaults.colors(checkedThumbColor = MantisGreen, checkedTrackColor = MantisGreen.copy(alpha=0.5f)))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Expandable List Items
            ExpandableRow("About Developer", "Designed and developed exclusively by and for Kush.")
            ExpandableRow("Feedback", "Your feedback is always welcome to improve Mantis.")
            ExpandableRow("FAQs", "Q: Does this use trackers?\nA: No, it is 100% offline and privacy-focused.")
            ExpandableRow("Legal Info", "Licensed under MIT. Open Source.")
            ExpandableRow("Contact Us", "Reach out via the GitHub repository.")
            ExpandableRow("About App", "Mantis Calculator v1.0\nA powerful 4-in-1 calculation suite.")
        }
    }
}

@Composable
fun ExpandableRow(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { expanded = !expanded }) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Icon(Icons.Filled.ArrowDropDown, contentDescription = "Expand")
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(content, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
