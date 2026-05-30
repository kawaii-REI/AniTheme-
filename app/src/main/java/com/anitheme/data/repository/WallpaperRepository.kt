package com.anitheme.data.repository

import com.anitheme.R
import com.anitheme.data.model.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperRepository @Inject constructor() {

    // =========================================================================
    // ⭐ ADD YOUR IMAGES HERE
    // After dropping images into res/drawable/, add them to these lists.
    // =========================================================================

    fun getWallpapers(): List<WallpaperItem> = buildList {

        // ── LIVE WALLPAPERS ──────────────────────────────────────────────────
        // These are rendered by the WallpaperService (no image file needed)

        add(WallpaperItem(
            id = "itsuki_star_field_live",
            name = "Itsuki — Star Night Live",
            character = QuintupletId.ITSUKI,
            type = WallpaperType.LIVE,
            thumbnailRes = null,      // add R.drawable.thumb_itsuki_star when you have it
            fullRes = null,
            liveRendererKey = "star_field",
            cpuPercent = 0.3f,
            supportsHomeScreen = true,
            supportsPowerSave = true,
        ))

        add(WallpaperItem(
            id = "sakura_petals_live",
            name = "Sakura Petals Live",
            character = null,
            type = WallpaperType.LIVE,
            thumbnailRes = null,
            fullRes = null,
            liveRendererKey = "sakura",
            cpuPercent = 0.4f,
            supportsHomeScreen = true,
            supportsPowerSave = true,
        ))

        add(WallpaperItem(
            id = "miku_waveform_live",
            name = "Miku — Lo-Fi Waveform",
            character = QuintupletId.MIKU,
            type = WallpaperType.LIVE,
            thumbnailRes = null,
            fullRes = null,
            liveRendererKey = "waveform",
            cpuPercent = 0.2f,
            supportsHomeScreen = true,
            supportsPowerSave = true,
        ))

        // ── STATIC WALLPAPERS ────────────────────────────────────────────────
        // ⬇️ UNCOMMENT AND ADD YOUR IMAGE RESOURCE IDs BELOW ⬇️

        // add(WallpaperItem(
        //     id = "itsuki_01",
        //     name = "Itsuki — Star Clips",
        //     character = QuintupletId.ITSUKI,
        //     type = WallpaperType.STATIC,
        //     thumbnailRes = R.drawable.itsuki_wall_01,   // same image is fine as thumb
        //     fullRes = R.drawable.itsuki_wall_01,
        // ))

        // add(WallpaperItem(
        //     id = "itsuki_02",
        //     name = "Itsuki — Eating",
        //     character = QuintupletId.ITSUKI,
        //     type = WallpaperType.STATIC,
        //     thumbnailRes = R.drawable.itsuki_wall_02,
        //     fullRes = R.drawable.itsuki_wall_02,
        // ))

        // add(WallpaperItem(
        //     id = "ichika_01",
        //     name = "Ichika — School",
        //     character = QuintupletId.ICHIKA,
        //     type = WallpaperType.STATIC,
        //     thumbnailRes = R.drawable.ichika_wall_01,
        //     fullRes = R.drawable.ichika_wall_01,
        // ))

        // Keep adding the same pattern for all your images...
    }

    fun getWallpaperById(id: String) = getWallpapers().find { it.id == id }

    fun getWallpapersByCharacter(character: QuintupletId) =
        getWallpapers().filter { it.character == character }

    fun getLiveWallpapers() =
        getWallpapers().filter { it.type == WallpaperType.LIVE }

    fun getStaticWallpapers() =
        getWallpapers().filter { it.type == WallpaperType.STATIC }
}
