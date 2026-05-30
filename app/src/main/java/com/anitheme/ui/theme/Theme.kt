package com.anitheme.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ─── AniTheme Color Palette ───────────────────────────────────────────────────

// Itsuki — primary brand
val ItsukiOrange     = Color(0xFFE8553E)
val ItsukiOrangeLight= Color(0xFFFF8C69)
val ItsukiOrangeDark = Color(0xFFB03020)

// Pink accent
val AniPink          = Color(0xFFFF6B9D)
val AniPinkLight     = Color(0xFFFFB3CC)

// Background layers
val DeepSpace        = Color(0xFF0D0519)
val DeepSpace2       = Color(0xFF120825)
val DeepSpace3       = Color(0xFF1A0A2E)
val CardSurface      = Color(0x0FFFFFFF)  // 6% white
val CardSurface2     = Color(0x1AFFFFFF)  // 10% white

// Star gold
val StarGold         = Color(0xFFFFD700)
val StarGoldMuted    = Color(0xFFDDBC44)

// Per-character colors
val IchikaColor      = Color(0xFFB46496)
val NinoColor        = Color(0xFF64B464)
val MikuColor        = Color(0xFFDCB432)
val YotsubaColor     = Color(0xFF6478DC)
val ItsukiColor      = Color(0xFFE8553E)

// ─── Material3 Dark Color Scheme ─────────────────────────────────────────────

private val DarkColorScheme = darkColorScheme(
    primary          = ItsukiOrange,
    onPrimary        = Color.White,
    primaryContainer = ItsukiOrangeDark,
    onPrimaryContainer = ItsukiOrangeLight,

    secondary        = AniPink,
    onSecondary      = Color.White,

    tertiary         = StarGold,
    onTertiary       = DeepSpace,

    background       = DeepSpace,
    onBackground     = Color.White,

    surface          = DeepSpace2,
    onSurface        = Color.White,
    surfaceVariant   = DeepSpace3,
    onSurfaceVariant = Color(0xB3FFFFFF),  // 70% white

    outline          = Color(0x40FF6B9D),  // 25% pink border
    outlineVariant   = Color(0x1AFFFFFF),  // 10% white border
)

// ─── Theme Composable ─────────────────────────────────────────────────────────

@Composable
fun AniThemeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AniTypography,
        content = content,
    )
}
