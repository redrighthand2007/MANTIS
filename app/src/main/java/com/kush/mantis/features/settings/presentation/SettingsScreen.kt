package com.kush.mantis.features.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kush.mantis.ui.theme.MantisGreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themeMode.collectAsState()
    val hapticFeedback by viewModel.hapticFeedback.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(text = "Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.height(24.dp))

        // Theme
        Text(text = "Appearance", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Theme", fontSize = 18.sp)
                Button(onClick = { viewModel.setThemeMode(if(themeMode == "Dark") "Light" else "Dark") }, colors = ButtonDefaults.buttonColors(containerColor = MantisGreen)) {
                    Text(themeMode, color = androidx.compose.ui.graphics.Color.Black)
                }
            }
        }

        // Haptics
        Text(text = "Calculation", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Haptic Feedback", fontSize = 18.sp)
                Switch(checked = hapticFeedback, onCheckedChange = { viewModel.setHapticFeedback(it) }, colors = SwitchDefaults.colors(checkedThumbColor = MantisGreen, checkedTrackColor = MantisGreen.copy(alpha=0.5f)))
            }
        }
        
        // About
        Text(text = "About", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Mantis Calculator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MantisGreen)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Version 1.0.0", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(modifier = Modifier.height(12.dp))
                Text("A privacy-focused, offline calculator featuring scientific, programmer, and conversion modes built with Jetpack Compose.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                
                Text("About Makers", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Designed and developed exclusively for Kush to provide a seamless, tracker-free calculation experience.", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}
