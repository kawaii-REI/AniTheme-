package com.anitheme.data.model

import androidx.annotation.DrawableRes

// ─── Character ───────────────────────────────────────────────────────────────

enum class QuintupletId { ICHIKA, NINO, MIKU, YOTSUBA, ITSUKI }

data class Character(
    val id: QuintupletId,
    val name: String,
    val number: Int,           // 1-5
    val colorHex: String,      // primary accent color for their theme
    val emoji: String,
    @DrawableRes val previewRes: Int? = null   // optional preview image
)

// Default quintuplets list — add your images by setting previewRes
val DefaultCharacters = listOf(
    Character(QuintupletId.ICHIKA,  "Ichika",  1, "#B46496", "🌺"),
    Character(QuintupletId.NINO,    "Nino",    2, "#64B464", "🍀"),
    Character(QuintupletId.MIKU,    "Miku",    3, "#DCB432", "🎧"),
    Character(QuintupletId.YOTSUBA, "Yotsuba", 4, "#6478DC", "📚"),
    Character(QuintupletId.ITSUKI,  "Itsuki",  5, "#E8553E", "⭐"),
)

// ─── Wallpaper ───────────────────────────────────────────────────────────────

enum class WallpaperType { STATIC, LIVE }

data class WallpaperItem(
    val id: String,
    val name: String,
    val character: QuintupletId?,        // null = generic anime
    val type: WallpaperType,
    @DrawableRes val thumbnailRes: Int?,
    @DrawableRes val fullRes: Int?,      // for static walls
    val liveRendererKey: String? = null, // for live walls: "star_field", "sakura", etc.
    val cpuPercent: Float = 0f,          // for live walls
    val supportsHomeScreen: Boolean = true,
    val supportsPowerSave: Boolean = true,
)

// ─── Theme Preset ─────────────────────────────────────────────────────────────

data class ThemePreset(
    val id: String,
    val name: String,
    val character: QuintupletId?,
    val description: String,
    val primaryColor: String,
    val backgroundColorDark: String,
    val emoji: String,
    val wallpaperIds: List<String>,      // bundled wallpapers
    val iconPackId: String?,
    val isFree: Boolean = true,
    val isActive: Boolean = false,
)

// ─── Icon Pack ───────────────────────────────────────────────────────────────

data class IconPackItem(
    val id: String,
    val name: String,
    val character: QuintupletId?,
    val styleKey: String,   // "itsuki_red", "star_night", "quintuplets_mixed"
    @DrawableRes val previewRes: Int?,
    val appCount: Int,       // how many apps have custom icons
    val isFree: Boolean = true,
)

// ─── User Preferences (saved to DataStore) ───────────────────────────────────

data class UserPrefs(
    val activeCharacter: QuintupletId = QuintupletId.ITSUKI,
    val activeThemeId: String = "itsuki_star_night",
    val activeWallpaperId: String = "itsuki_star_field_live",
    val activeIconPackId: String = "itsuki_red",
    val powerSaveModeEnabled: Boolean = false,
    val homeScreenLiveWallEnabled: Boolean = true,
    val autoThemeCycleEnabled: Boolean = false,
)
