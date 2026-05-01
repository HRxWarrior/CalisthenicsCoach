package com.caliscoach.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkScheme = darkColorScheme(
    primary = AccentGreen, secondary = AccentBlue, tertiary = AccentOrange,
    background = DarkBg, surface = DarkSurface,
    onPrimary = DarkBg, onSecondary = DarkBg, onBackground = TextPrimary,
    onSurface = TextPrimary, error = AccentRed, onError = DarkBg,
)

@Composable
fun CalisTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkScheme, typography = CalisTypography, content = content)
}
