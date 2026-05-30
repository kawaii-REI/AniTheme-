package com.anitheme.data.repository

import com.anitheme.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepository @Inject constructor() {

    fun getThemePresets(): List<ThemePreset> = listOf(

        ThemePreset(
            id = "itsuki_star_night",
            name = "Itsuki — Star Night",
            character = QuintupletId.ITSUKI,
            description = "Dark space theme with warm red-orange accents. Itsuki's star hair clips everywhere.",
            primaryColor = "#E8553E",
            backgroundColorDark = "#0D0519",
            emoji = "⭐",
            wallpaperIds = listOf("itsuki_star_field_live", "itsuki_01", "itsuki_02"),
            iconPackId = "itsuki_red",
            isFree = true,
        ),

        ThemePreset(
            id = "ichika_night_club",
            name = "Ichika — Night Club",
            character = QuintupletId.ICHIKA,
            description = "Deep purple moody vibes. Ichika's actress energy.",
            primaryColor = "#B46496",
            backgroundColorDark = "#0F0518",
            emoji = "🌺",
            wallpaperIds = listOf("ichika_01"),
            iconPackId = "quintuplets_mixed",
            isFree = true,
        ),

        ThemePreset(
            id = "nino_garden",
            name = "Nino — Garden",
            character = QuintupletId.NINO,
            description = "Fresh green with sakura pink accents. Nino's cooking & nature side.",
            primaryColor = "#64B464",
            backgroundColorDark = "#051A0D",
            emoji = "🍀",
            wallpaperIds = listOf("sakura_petals_live", "nino_01"),
            iconPackId = null,
            isFree = true,
        ),

        ThemePreset(
            id = "miku_lofi",
            name = "Miku — Lo-Fi",
            character = QuintupletId.MIKU,
            description = "Warm amber tones, headphone vibes, study chill energy.",
            primaryColor = "#DCB432",
            backgroundColorDark = "#120A00",
            emoji = "🎧",
            wallpaperIds = listOf("miku_waveform_live", "miku_01"),
            iconPackId = null,
            isFree = true,
        ),

        ThemePreset(
            id = "yotsuba_sporty",
            name = "Yotsuba — Sporty",
            character = QuintupletId.YOTSUBA,
            description = "Blue & white, energetic, always running. Yotsuba's athletic spirit.",
            primaryColor = "#6478DC",
            backgroundColorDark = "#05091A",
            emoji = "📚",
            wallpaperIds = listOf("yotsuba_01"),
            iconPackId = null,
            isFree = true,
        ),

        ThemePreset(
            id = "quintuplets_all",
            name = "All Five Sisters",
            character = null,
            description = "Cycles through all five quintuplets daily. Full set theme.",
            primaryColor = "#FF6B9D",
            backgroundColorDark = "#0A0A1A",
            emoji = "🌸",
            wallpaperIds = listOf("itsuki_01", "ichika_01", "nino_01", "miku_01", "yotsuba_01"),
            iconPackId = "quintuplets_mixed",
            isFree = true,
        ),
    )

    fun getThemeById(id: String) = getThemePresets().find { it.id == id }

    fun getIconPacks(): List<IconPackItem> = listOf(
        IconPackItem(
            id = "itsuki_red",
            name = "Itsuki — Warm Red",
            character = QuintupletId.ITSUKI,
            styleKey = "itsuki_red",
            previewRes = null,
            appCount = 60,
        ),
        IconPackItem(
            id = "star_night",
            name = "Star Night Dark",
            character = null,
            styleKey = "star_night",
            previewRes = null,
            appCount = 60,
        ),
        IconPackItem(
            id = "quintuplets_mixed",
            name = "Quintuplets Mixed",
            character = null,
            styleKey = "quintuplets_mixed",
            previewRes = null,
            appCount = 100,
        ),
    )
}
