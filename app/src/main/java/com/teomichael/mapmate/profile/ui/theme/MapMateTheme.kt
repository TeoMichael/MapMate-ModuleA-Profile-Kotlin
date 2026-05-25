package com.teomichael.mapmate.profile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MapMateLightColors: ColorScheme = lightColorScheme(
    primary = Color(0xFF176B5B),
    onPrimary = Color.White,
    secondary = Color(0xFF335C81),
    tertiary = Color(0xFF8C5E2A),
    background = Color(0xFFF7F7F2),
    surface = Color.White,
    surfaceVariant = Color(0xFFE8E6DD),
    onSurface = Color(0xFF1D1F1E),
    onSurfaceVariant = Color(0xFF505750)
)

@Composable
fun MapMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MapMateLightColors,
        typography = MaterialTheme.typography,
        content = content
    )
}

