package com.example.conjunto.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    onPrimary = OnPrimaryBlue,
    primaryContainer = PrimaryContainerBlue,
    onPrimaryContainer = PrimaryBlue,
    secondary = SecondaryTeal,
    onSecondary = OnPrimaryBlue,
    secondaryContainer = SecondaryContainerTeal,
    onSecondaryContainer = OnSecondaryTeal,
    background = BackgroundSlate,
    onBackground = OnSurfaceText,
    surface = SurfaceWhite,
    onSurface = OnSurfaceText,
    onSurfaceVariant = OnSurfaceVariantText,
    error = ErrorRed,
    errorContainer = ErrorContainerRed,
    onError = OnPrimaryBlue
)

@Composable
fun ConjuntoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
