package com.anitheme.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import coil.compose.AsyncImage
import com.anitheme.data.model.*
import com.anitheme.ui.theme.*

// ─── CharacterCard ────────────────────────────────────────────────────────────
// The quintuplet selector card shown in the horizontal row

@Composable
fun CharacterCard(
    character: Character,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (isSelected)
        Color(android.graphics.Color.parseColor(character.colorHex))
    else Color.Transparent

    val glowAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0.5f else 0f,
        animationSpec = tween(300),
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .shadow(
                    elevation = if (isSelected) 8.dp else 0.dp,
                    shape = RoundedCornerShape(12.dp),
                    ambientColor = Color(android.graphics.Color.parseColor(character.colorHex))
                        .copy(alpha = glowAlpha),
                    spotColor = Color(android.graphics.Color.parseColor(character.colorHex))
                        .copy(alpha = glowAlpha),
                )
                .background(
                    color = Color(android.graphics.Color.parseColor(character.colorHex))
                        .copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                )
                .border(
                    width = if (isSelected) 2.dp else 0.5.dp,
                    color = borderColor.copy(alpha = if (isSelected) 1f else 0.3f),
                    shape = RoundedCornerShape(12.dp),
                )
        ) {
            if (character.previewRes != null) {
                Image(
                    painter = painterResource(character.previewRes),
                    contentDescription = character.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                )
            } else {
                Text(character.emoji, fontSize = 28.sp)
            }
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = character.name,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected)
                Color(android.graphics.Color.parseColor(character.colorHex))
            else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        )

        Text(
            text = "#${character.number}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
        )
    }
}

// ─── WallpaperCard ────────────────────────────────────────────────────────────

@Composable
fun WallpaperCard(
    item: WallpaperItem,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .width(100.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(DeepSpace3)
                .border(
                    width = if (isSelected) 2.dp else 0.5.dp,
                    color = if (isSelected) AniPink else Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(10.dp),
                )
        ) {
            if (item.thumbnailRes != null) {
                Image(
                    painter = painterResource(item.thumbnailRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                // Placeholder for live wallpapers
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                        .background(
                            Brush.verticalGradient(listOf(DeepSpace, DeepSpace3))
                        )
                ) {
                    Text(
                        text = if (item.type == WallpaperType.LIVE) "⚡" else "🖼️",
                        fontSize = 24.sp,
                    )
                }
            }

            // CPU badge for live walls
            if (item.type == WallpaperType.LIVE) {
                Text(
                    text = "~${item.cpuPercent}% CPU",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 8.sp),
                    color = Color(0xFF6DC476),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(4.dp)
                        .background(
                            color = Color(0xFF6DC476).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp),
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                )
            }
        }

        if (isSelected) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-4).dp)
                    .background(AniPink, CircleShape),
            ) {
                Text("✓", fontSize = 10.sp, color = Color.White)
            }
        }

        Spacer(Modifier.height(4.dp))
        Text(
            text = item.name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 74.dp)
                .padding(horizontal = 2.dp),
        )
    }
}

// ─── SectionHeader ───────────────────────────────────────────────────────────

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    action: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text("★ ", color = StarGold, fontSize = 12.sp)
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f),
        )
        if (action != null) {
            Text(
                text = action,
                style = MaterialTheme.typography.labelSmall,
                color = AniPink,
                modifier = Modifier.clickable { onAction?.invoke() },
            )
        }
    }
}

// ─── AniChip — small tag/badge ───────────────────────────────────────────────

@Composable
fun AniChip(
    text: String,
    color: Color = StarGold,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.12f),
                shape = RoundedCornerShape(6.dp),
            )
            .border(0.5.dp, color.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
            .padding(horizontal = 7.dp, vertical = 3.dp),
    )
}

// ─── PowerSaveToggleCard ──────────────────────────────────────────────────────

@Composable
fun SettingToggleCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(CardSurface, RoundedCornerShape(12.dp))
            .border(0.5.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            .padding(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = AniPink,
                uncheckedThumbColor = Color.White.copy(alpha = 0.5f),
                uncheckedTrackColor = Color.White.copy(alpha = 0.1f),
            ),
        )
    }
}
