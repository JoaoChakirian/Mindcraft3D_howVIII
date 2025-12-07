package com.example.mindcraft3d.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = MindcraftDarkBlue,
    onPrimary = MindcraftWhite,
    secondary = MindcraftDarkGray,
    onSecondary = MindcraftWhite,
    background = MindcraftBlack,
    onBackground = MindcraftWhite,
    surface = MindcraftDarkGray,
    onSurface = MindcraftWhite
)

private val LightColorScheme = lightColorScheme(
    primary = MindcraftBlue,
    onPrimary = MindcraftWhite,
    secondary = MindcraftLightGray,
    onSecondary = MindcraftBlack,
    background = MindcraftLightGray, // Alterado para cinza claro
    onBackground = MindcraftBlack,
    surface = MindcraftWhite, // Cards continuam brancos
    onSurface = MindcraftBlack
)

@Composable
fun Mindcraft3DTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}