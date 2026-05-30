package com.anitheme.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import com.anitheme.data.model.*
import com.anitheme.data.repository.ThemeRepository
import com.anitheme.ui.components.*
import com.anitheme.ui.theme.*

@Composable
fun ThemesScreen(
    repository: ThemeRepository = ThemeRepository(),
    activeThemeId: String = "itsuki_star_night",
    onApplyTheme: (ThemePreset) -> Unit = {},
) {
    val themes = remember { repository.getThemePresets() }

    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp),
            ) {
                Text("★ ", color = StarGold, fontSize = 12.sp)
                Text("Full Themes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                AniChip("ALL FREE", color = Color(0xFF6DC476))
            }
        }
        items(themes) { theme ->
            ThemeRow(
                theme = theme,
                isActive = theme.id == activeThemeId,
                onApply = { onApplyTheme(theme) },
            )
        }
    }
}

@Composable
private fun ThemeRow(theme: ThemePreset, isActive: Boolean, onApply: () -> Unit) {
    val accent = Color(android.graphics.Color.parseColor(theme.primaryColor))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .background(
                if (isActive) accent.copy(alpha = 0.12f) else CardSurface,
                RoundedCornerShape(14.dp),
            )
            .border(
                if (isActive) 1.dp else 0.5.dp,
                if (isActive) accent.copy(alpha = 0.5f) else Color.White.copy(alpha = 0.06f),
                RoundedCornerShape(14.dp),
            )
            .clickable(onClick = onApply)
            .padding(14.dp),
    ) {
        Text(theme.emoji, fontSize = 30.sp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(theme.name, style = MaterialTheme.typography.titleMedium, color = if (isActive) accent else Color.White)
            Text(theme.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(top = 2.dp))
            Row(modifier = Modifier.padding(top = 6.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                if (isActive) AniChip("Active ✓", color = Color(0xFF6DC476))
                if (theme.isFree) AniChip("FREE", color = Color(0xFF6DC476))
            }
        }
        if (isActive) Text("✓", color = Color(0xFF6DC476), fontSize = 18.sp)
    }
}
