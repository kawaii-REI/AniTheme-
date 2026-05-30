package com.anitheme.ui.screens

import android.app.WallpaperManager
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import com.anitheme.data.model.*
import com.anitheme.data.repository.WallpaperRepository
import com.anitheme.ui.components.*
import com.anitheme.ui.theme.*

@Composable
fun WallpapersScreen(
    repository: WallpaperRepository = WallpaperRepository(),
    selectedWallpaperId: String = "itsuki_star_field_live",
    onSelectWallpaper: (WallpaperItem) -> Unit = {},
) {
    val context = LocalContext.current
    val wallpapers = remember { repository.getWallpapers() }
    val liveWalls = wallpapers.filter { it.type == WallpaperType.LIVE }
    val staticWalls = wallpapers.filter { it.type == WallpaperType.STATIC }

    // Image picker for user uploads
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // TODO: Save to app storage and add to user's custom walls
        }
    }

    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {

        // ── Presets Row ──────────────────────────────────────────────────────
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text("★ ", color = StarGold, fontSize = 12.sp)
                Text("Itsuki Presets", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                AniChip("ALL FREE", color = Color(0xFF6DC476))
            }
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(5) { i ->
                    val presetNames = listOf("Star Night","Sakura","Autumn","Ocean","Moon")
                    val presetEmojis = listOf("⭐","🌸","🍂","🌊","🌙")
                    PresetChip(presetNames[i], presetEmojis[i], i == 0)
                }
            }
        }

        // ── Live Wallpapers ──────────────────────────────────────────────────
        item {
            SectionHeader(title = "⚡ Live Walls — Ultra Optimized", modifier = Modifier.padding(top = 12.dp))
        }
        items(liveWalls) { wall ->
            LiveWallpaperRow(
                item = wall,
                isSelected = wall.id == selectedWallpaperId,
                onApply = {
                    onSelectWallpaper(wall)
                    // Open system live wallpaper chooser
                    try {
                        context.startActivity(
                            Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                        )
                    } catch (e: Exception) { }
                },
            )
        }

        // ── Static Walls ─────────────────────────────────────────────────────
        if (staticWalls.isNotEmpty()) {
            item { SectionHeader(title = "Static Wallpapers", modifier = Modifier.padding(top = 12.dp)) }
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(staticWalls) { wall ->
                        WallpaperCard(
                            item = wall,
                            isSelected = wall.id == selectedWallpaperId,
                            onClick = { onSelectWallpaper(wall) },
                        )
                    }
                }
            }
        }

        // ── Upload Your Own ──────────────────────────────────────────────────
        item {
            SectionHeader(title = "📸 Upload Your Own", modifier = Modifier.padding(top = 12.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(100.dp)
                    .border(
                        1.5.dp,
                        AniPink.copy(alpha = 0.3f),
                        RoundedCornerShape(14.dp),
                    )
                    .background(AniPink.copy(alpha = 0.03f), RoundedCornerShape(14.dp))
                    .clickable { imagePicker.launch("image/*") },
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🖼️", fontSize = 28.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("Drop your Itsuki art here", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "JPG, PNG, GIF • Any size",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun PresetChip(name: String, emoji: String, selected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(88.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DeepSpace3)
                .border(
                    if (selected) 2.dp else 0.5.dp,
                    if (selected) AniPink else Color.White.copy(alpha = 0.1f),
                    RoundedCornerShape(10.dp),
                ),
        ) {
            Text(emoji, fontSize = 24.sp)
        }
        Text(
            name,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) AniPink else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}

@Composable
private fun LiveWallpaperRow(
    item: WallpaperItem,
    isSelected: Boolean,
    onApply: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .background(CardSurface, RoundedCornerShape(12.dp))
            .border(
                if (isSelected) 1.dp else 0.5.dp,
                if (isSelected) AniPink.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.06f),
                RoundedCornerShape(12.dp),
            )
            .padding(12.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(50.dp)
                .background(DeepSpace3, RoundedCornerShape(10.dp)),
        ) {
            Text(text = when (item.liveRendererKey) {
                "star_field" -> "⭐"
                "sakura"     -> "🌸"
                "waveform"   -> "🎵"
                else         -> "✨"
            }, fontSize = 22.sp)
        }

        Spacer(Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                AniChip("~${item.cpuPercent}% CPU", color = Color(0xFF6DC476))
                if (item.supportsHomeScreen) AniChip("Home ✓", color = StarGold)
                if (item.supportsPowerSave)  AniChip("Power Save ✓", color = Color(0xFF6DC476))
            }
        }

        Spacer(Modifier.width(8.dp))

        TextButton(onClick = onApply) {
            Text(
                if (isSelected) "Active ✓" else "Apply",
                color = if (isSelected) Color(0xFF6DC476) else AniPink,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}
