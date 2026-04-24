package com.example.agendacontactosgrhr.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = StardewOrange,
    secondary = StardewBlue,
    tertiary = StardewCream,
    background = StardewAppleGreen,
    surface = StardewAppleGreen,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = StardewBrown,
    onBackground = StardewCream,
    onSurface = StardewCream
)

private val LightColorScheme = lightColorScheme(
    primary = StardewLightBrown,
    secondary = StardewAppleGreen,
    tertiary = StardewTeal,
    background = StardewCream,
    surface = Color(0xFFFFF9E6), // A slightly lighter cream for surfaces
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = StardewBrown,
    onSurface = StardewBrown
)

@Composable
fun AgendaContactosGRHRTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false to force Stardew colors instead of Android dynamic colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
