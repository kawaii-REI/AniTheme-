# AniTheme — Quintuplet Edition
## Android App Project Structure Guide

---

## 📁 FULL FOLDER TREE

```
AniTheme/
├── app/
│   └── src/
│       └── main/
│           ├── java/com/anitheme/
│           │   ├── MainActivity.kt                  ← App entry point
│           │   ├── AniThemeApplication.kt           ← App class (Hilt)
│           │   │
│           │   ├── ui/
│           │   │   ├── theme/
│           │   │   │   ├── Theme.kt                 ← Material3 color scheme
│           │   │   │   ├── Color.kt                 ← All color constants
│           │   │   │   └── Type.kt                  ← Font definitions
│           │   │   │
│           │   │   ├── screens/
│           │   │   │   ├── HomeScreen.kt            ← Main home tab
│           │   │   │   ├── WallpapersScreen.kt      ← Wallpaper browser
│           │   │   │   ├── IconsScreen.kt           ← Icon pack browser
│           │   │   │   ├── ThemesScreen.kt          ← Full theme browser
│           │   │   │   └── MineScreen.kt            ← Profile + settings
│           │   │   │
│           │   │   └── components/
│           │   │       ├── BottomNav.kt             ← Bottom navigation bar
│           │   │       ├── CharacterCard.kt         ← Quintuplet selector card
│           │   │       ├── WallpaperCard.kt         ← Wallpaper preview card
│           │   │       ├── PresetCard.kt            ← Theme preset card
│           │   │       └── IconBubble.kt            ← App icon preview
│           │   │
│           │   ├── data/
│           │   │   ├── model/
│           │   │   │   ├── Character.kt             ← Quintuplet data class
│           │   │   │   ├── WallpaperItem.kt         ← Wallpaper data class
│           │   │   │   ├── IconPack.kt              ← Icon pack data class
│           │   │   │   └── ThemePreset.kt           ← Theme data class
│           │   │   │
│           │   │   └── repository/
│           │   │       ├── WallpaperRepository.kt   ← Wallpaper data source
│           │   │       └── ThemeRepository.kt       ← Theme data source
│           │   │
│           │   ├── wallpaper/
│           │   │   ├── AniLiveWallpaperService.kt   ← THE MAIN LIVE WALLPAPER ENGINE
│           │   │   ├── StarFieldRenderer.kt         ← Itsuki star night renderer
│           │   │   ├── SakuraRenderer.kt            ← Falling petals renderer
│           │   │   └── WallpaperEngine.kt           ← Base engine (handles power save)
│           │   │
│           │   ├── icons/
│           │   │   └── IconPackManager.kt           ← Handles icon pack switching
│           │   │
│           │   └── utils/
│           │       ├── BatteryOptimizer.kt          ← Detects power save mode
│           │       └── ImageUtils.kt                ← Image loading helpers
│           │
│           ├── res/
│           │   ├── drawable/                        ← ⭐ YOUR IMAGES GO HERE
│           │   │   ├── itsuki_wall_01.jpg           ← Add your Itsuki images here
│           │   │   ├── itsuki_wall_02.jpg
│           │   │   ├── ichika_wall_01.jpg
│           │   │   ├── nino_wall_01.jpg
│           │   │   ├── yotsuba_wall_01.jpg
│           │   │   ├── miku_wall_01.jpg
│           │   │   └── ic_launcher_foreground.xml  ← App icon (Itsuki)
│           │   │
│           │   ├── mipmap-hdpi/                     ← App launcher icons (all densities)
│           │   │   └── ic_launcher.png              ← Generate from your Itsuki art
│           │   │
│           │   ├── values/
│           │   │   ├── strings.xml
│           │   │   └── colors.xml
│           │   │
│           │   └── xml/
│           │       └── wallpaper.xml                ← Live wallpaper metadata
│           │
│           └── AndroidManifest.xml
│
├── build.gradle.kts                                 ← Project build file
├── app/build.gradle.kts                             ← App build file
├── gradle/libs.versions.toml                        ← Dependency versions
└── settings.gradle.kts
```

---

## 🖼️ HOW TO ADD YOUR IMAGES

### Step 1 — Wallpapers
Put your Itsuki/quintuplet images in:
```
app/src/main/res/drawable/
```
Name them like:
```
itsuki_wall_01.jpg   ← must be lowercase, no spaces
itsuki_wall_02.jpg
ichika_wall_01.jpg
nino_wall_01.jpg
miku_wall_01.jpg
yotsuba_wall_01.jpg
```
Recommended size: 1080×2340px (FHD+ portrait). JPG for photos, PNG for art with transparency.

### Step 2 — App Icon (Itsuki as the launcher icon)
1. In Android Studio: right-click `res/` → New → Image Asset
2. Choose "Launcher Icons (Adaptive and Legacy)"
3. For the foreground layer, upload your Itsuki image
4. Set background color to `#1A0A2E` (dark purple-black, matches the app theme)
5. This auto-generates all mipmap sizes for you

### Step 3 — Register images in the repository
Open `WallpaperRepository.kt` and add your images to the list:
```kotlin
R.drawable.itsuki_wall_01,
R.drawable.itsuki_wall_02,
// etc
```

---

## 📦 TECH STACK

| What | Tech |
|------|------|
| Language | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Live Wallpaper | WallpaperService + Canvas (NOT OpenGL — saves battery) |
| Navigation | Compose Navigation |
| Images | Coil 3 |
| DI | Hilt |
| Min SDK | API 21 (Android 5.0) — works on very old devices |
| Target SDK | API 35 |

---

## ⚡ LIVE WALLPAPER ON HOME SCREEN (the hard part)

Most apps only do live wallpapers on the lock screen. To get it on the home screen:
- The `AniLiveWallpaperService` uses `WallpaperService` (system-level)
- User needs to go: **Settings → Wallpaper → Live Wallpapers → AniTheme**
- Our app has a deep-link button that opens this screen directly
- We use `Canvas` not `OpenGL` — this is what keeps CPU at under 0.5%
- When battery saver is ON, we auto-reduce frame rate from 30fps to 5fps

---

## 🚀 BUILD ORDER (do these in order)

1. Set up `libs.versions.toml` (dependencies)
2. `build.gradle.kts` files (project + app)  
3. `AndroidManifest.xml` (permissions + service declaration)
4. `data/model/` files (data classes)
5. `ui/theme/` files (colors, fonts)
6. `wallpaper/` files (live wallpaper engine)
7. `ui/components/` files (reusable UI)
8. `ui/screens/` files (the 5 screens)
9. `MainActivity.kt` (ties it all together)
10. Add your images to `res/drawable/`
