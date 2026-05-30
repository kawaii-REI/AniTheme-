package com.anitheme.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.anitheme.ui.components.*
import com.anitheme.ui.theme.*

@Composable
fun IconsScreen() {
    val apps = listOf("📷 Camera","📱 Phone","💬 Messages","🎵 Music","🌐 Browser","📧 Email","🗺️ Maps","🕐 Clock","⚙️ Settings","📂 Files","🔋 Battery","📸 Gallery","🎮 Games","📅 Calendar","🔔 Notifs","➕ Add App")
    var selectedStyle by remember { mutableStateOf(0) }
    val styles = listOf("Itsuki — Red","Quintuplets","Star Night")

    Column {
        SectionHeader(title = "Anime Icon Styles", modifier = Modifier.padding(top = 8.dp))
        Row(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            styles.forEachIndexed { i, s ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                        .background(if (i == selectedStyle) ItsukiOrange.copy(.2f) else CardSurface, RoundedCornerShape(10.dp))
                        .border(1.dp, if (i == selectedStyle) ItsukiOrange.copy(.5f) else Color.White.copy(.06f), RoundedCornerShape(10.dp))
                        .clickable { selectedStyle = i }
                        .padding(vertical = 8.dp),
                ) { Text(s, style = MaterialTheme.typography.labelSmall, color = if (i == selectedStyle) ItsukiOrangeLight else MaterialTheme.colorScheme.onSurfaceVariant) }
            }
        }
        Spacer(Modifier.height(12.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.weight(1f),
        ) {
            items(apps) { app ->
                val parts = app.split(" ", limit = 2)
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(58.dp)
                            .background(ItsukiOrange.copy(.1f), RoundedCornerShape(14.dp))
                            .border(1.5.dp, ItsukiOrange.copy(.3f), RoundedCornerShape(14.dp)),
                    ) { Text(parts[0], fontSize = 26.sp) }
                    Text(parts.getOrElse(1){""}, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
        // Apply button
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = ItsukiOrange),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) { Text("Apply Icon Pack ✨") }
    }
}
