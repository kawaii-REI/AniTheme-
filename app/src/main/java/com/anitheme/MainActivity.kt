package com.anitheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.navigation.compose.*
import com.anitheme.data.model.*
import com.anitheme.ui.components.*
import com.anitheme.ui.screens.*
import com.anitheme.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AniThemeTheme {
                AniThemeApp()
            }
        }
    }
}

@Composable
fun AniThemeApp() {
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryAsState()
    var selectedCharacter by remember { mutableStateOf(DefaultCharacters.last()) } // Itsuki = last (#5)

    val tabs = listOf(
        Triple("home",       "🏠", "Home"),
        Triple("wallpapers", "🖼️", "Walls"),
        Triple("icons",      "✨", "Icons"),
        Triple("themes",     "🎨", "Themes"),
        Triple("mine",       "💖", "Mine"),
    )

    Scaffold(
        containerColor = DeepSpace,
        bottomBar = {
            NavigationBar(containerColor = DeepSpace.copy(alpha = 0.95f), tonalElevation = 0.dp) {
                tabs.forEach { (route, emoji, label) ->
                    NavigationBarItem(
                        selected = currentRoute?.destination?.route == route,
                        onClick = { navController.navigate(route) { launchSingleTop = true; popUpTo("home") } },
                        icon = { Text(emoji, fontSize = 20.sp) },
                        label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedTextColor = AniPink,
                            indicatorColor = AniPink.copy(alpha = 0.15f),
                        ),
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding).fillMaxSize().background(DeepSpace),
        ) {
            composable("home") {
                HomeScreen(
                    characters = DefaultCharacters,
                    selectedCharacter = selectedCharacter,
                    onCharacterSelect = { selectedCharacter = it },
                    onNavigateToWallpapers = { navController.navigate("wallpapers") },
                    onNavigateToIcons = { navController.navigate("icons") },
                    onNavigateToThemes = { navController.navigate("themes") },
                )
            }
            composable("wallpapers") { WallpapersScreen() }
            composable("icons")      { IconsScreen() }
            composable("themes")     { ThemesScreen() }
            composable("mine")       { MineScreen() }
        }
    }
}
