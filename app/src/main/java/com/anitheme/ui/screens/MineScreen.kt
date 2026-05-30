package com.anitheme.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.anitheme.ui.components.*
import com.anitheme.ui.theme.*

@Composable
fun MineScreen() {
    var powerSave by remember { mutableStateOf(false) }
    var homeScreenLive by remember { mutableStateOf(true) }
    var autoTheme by remember { mutableStateOf(false) }

    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        item {
            // Profile header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth()
                    .background(ItsukiOrange.copy(.12f), RoundedCornerShape(14.dp))
                    .border(.5.dp, ItsukiOrange.copy(.25f), RoundedCornerShape(14.dp))
                    .padding(16.dp),
            ) {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.size(52.dp)
                        .background(Brush.linearGradient(listOf(ItsukiOrange, AniPink)), CircleShape)
                ) { Text("⭐", fontSize = 26.sp) }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("Itsuki Fan 💕", style = MaterialTheme.typography.titleMedium)
                    Text("★ Quintuplet Fandom", color = StarGold, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 2.dp))
                }
            }
        }
        item {
            // Stats
            Row(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("12" to "WALLS", "3" to "THEMES", "47" to "ICONS").forEach { (n, l) ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                            .background(CardSurface, RoundedCornerShape(12.dp))
                            .border(.5.dp, Color.White.copy(.06f), RoundedCornerShape(12.dp))
                            .padding(10.dp),
                    ) {
                        Text(n, style = MaterialTheme.typography.headlineLarge, color = AniPink)
                        Text(l, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Spacer(Modifier.height(12.dp))
        }
        item { SectionHeader(title = "⚙️ App Settings") }
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                SettingToggleCard("Power Save Mode", "Reduces animations for older devices", powerSave) { powerSave = it }
                SettingToggleCard("Home Screen Live Wall", "Enable live wallpaper on home screen", homeScreenLive) { homeScreenLive = it }
                SettingToggleCard("Auto Theme Cycle", "Rotate all 5 sisters daily", autoTheme) { autoTheme = it }
            }
        }
    }
}
