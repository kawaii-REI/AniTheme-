package com.anitheme.ui.screens

import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.anitheme.data.model.*
import com.anitheme.ui.components.*
import com.anitheme.ui.theme.*

@Composable
fun HomeScreen(
    characters: List<Character>,
    selectedCharacter: Character,
    onCharacterSelect: (Character) -> Unit,
    onNavigateToWallpapers: () -> Unit,
    onNavigateToIcons: () -> Unit,
    onNavigateToThemes: () -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 80.dp),
    ) {

        // ── Hero Banner ──────────────────────────────────────────────────────
        item {
            HeroBanner(
                character = selectedCharacter,
                onApplyTheme = onNavigateToThemes,
            )
        }

        // ── Quintuplet Selector ──────────────────────────────────────────────
        item {
            SectionHeader(
                title = "Choose Your Waifu",
                action = "All chars →",
                modifier = Modifier.padding(top = 12.dp),
            )
        }
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(characters) { char ->
                    CharacterCard(
                        character = char,
                        isSelected = char.id == selectedCharacter.id,
                        onClick = { onCharacterSelect(char) },
                    )
                }
            }
        }

        // ── Live Wallpaper Card ──────────────────────────────────────────────
        item {
            SectionHeader(title = "⚡ Live Wallpaper", modifier = Modifier.padding(top = 12.dp))
            LiveWallpaperCard()
        }

        // ── Feature Grid ────────────────────────────────────────────────────
        item {
            SectionHeader(title = "Customize", modifier = Modifier.padding(top = 12.dp))
            FeatureGrid(
                onWallpapers = onNavigateToWallpapers,
                onIcons = onNavigateToIcons,
                onThemes = onNavigateToThemes,
            )
        }
    }
}

// ─── Hero Banner ─────────────────────────────────────────────────────────────

@Composable
private fun HeroBanner(character: Character, onApplyTheme: () -> Unit) {
    val accentColor = Color(android.graphics.Color.parseColor(character.colorHex))

    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1A0510),
                        Color(0xFF2D0820),
                        Color(0xFF1A0A35),
                    )
                )
            )
            .border(1.dp, accentColor.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Left: character image area
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight()
            ) {
                if (character.previewRes != null) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(character.previewRes),
                        contentDescription = character.name,
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    Text(character.emoji, fontSize = 56.sp)
                }
            }

            // Right: text content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                AniChip("★ FEATURED CHARACTER", color = accentColor)

                Column {
                    Text(
                        text = character.name,
                        style = MaterialTheme.typography.displayLarge.copy(fontSize = 26.sp),
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        lineHeight = 28.sp,
                    )
                    Text(
                        text = "Go-toubun no Hanayome",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.6f),
                        modifier = Modifier.padding(top = 2.dp),
                    )
                }

                Button(
                    onClick = onApplyTheme,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accentColor,
                        contentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.height(32.dp),
                ) {
                    Text("Apply Theme ✨", style = MaterialTheme.typography.labelSmall)
                }
            }
        }

        // Decorative stars top-right
        Text(
            text = "★\n✦\n★",
            color = StarGold.copy(alpha = 0.4f),
            fontSize = 12.sp,
            lineHeight = 18.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp),
        )
    }
}

// ─── Live Wallpaper Quick-Set Card ──────────────────────────────────────────

@Composable
private fun LiveWallpaperCard() {
    val context = LocalContext.current
    var enabled by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(Color(0x33001428), Color(0x33100020))
                ),
                RoundedCornerShape(14.dp),
            )
            .border(0.5.dp, Color(0x33648CFF), RoundedCornerShape(14.dp))
            .padding(14.dp),
    ) {
        Text("🌟", fontSize = 28.sp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Itsuki Star Night — Live", style = MaterialTheme.typography.titleMedium)
            Text(
                "Under 0.5% CPU. Works on home screen.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
            AniChip("⚡ Ultra Low Power", color = Color(0xFF6DC476), modifier = Modifier.padding(top = 5.dp))
        }
        Switch(
            checked = enabled,
            onCheckedChange = {
                enabled = it
                if (it) {
                    // Deep-link to system live wallpaper picker
                    try {
                        val intent = Intent(android.app.WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                        context.startActivity(intent)
                    } catch (e: Exception) { /* fallback */ }
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF4FACFE),
            ),
        )
    }
}

// ─── Feature Grid ────────────────────────────────────────────────────────────

@Composable
private fun FeatureGrid(
    onWallpapers: () -> Unit,
    onIcons: () -> Unit,
    onThemes: () -> Unit,
) {
    val features = listOf(
        Triple("🖼️", "Wallpapers",  "120+ anime walls") to onWallpapers,
        Triple("🎀", "App Icons",   "Anime icon packs") to onIcons,
        Triple("🎨", "Themes",      "Full system themes") to onThemes,
        Triple("🪄", "Widgets",     "Clock & photo widgets") to {},
        Triple("📲", "Lock Screen", "Custom lock screen") to {},
        Triple("🔊", "Sounds",      "Anime notifications") to {},
    )

    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        features.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { (info, action) ->
                    FeatureCard(
                        emoji = info.first,
                        title = info.second,
                        desc = info.third,
                        onClick = action,
                        modifier = Modifier.weight(1f),
                    )
                }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun FeatureCard(
    emoji: String, title: String, desc: String,
    onClick: () -> Unit, modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(CardSurface, RoundedCornerShape(12.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(14.dp),
    ) {
        Text(emoji, fontSize = 24.sp)
        Spacer(Modifier.height(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(
            desc,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
}
