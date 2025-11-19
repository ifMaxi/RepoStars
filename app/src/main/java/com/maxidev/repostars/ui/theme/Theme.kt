package com.maxidev.repostars.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BluePrimaryLight,
    onPrimaryContainer = BluePrimaryDark,
    inversePrimary = BluePrimaryInverse,
    secondary = NeutralSecondary,
    onSecondary = Color.White,
    secondaryContainer = NeutralSecondaryContainer,
    onSecondaryContainer = NeutralSecondaryDark,
    tertiary = PurpleTertiary,
    onTertiary = Color.White,
    tertiaryContainer = PurpleTertiaryLight,
    onTertiaryContainer = PurpleTertiaryDark,
    background = BackgroundLight,
    onBackground = OnBackgroundLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    surfaceTint = SurfaceTintLight,
    inverseSurface = InverseSurfaceLight,
    inverseOnSurface = InverseOnSurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    scrim = Scrim,
    surfaceBright = SurfaceBrightLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceDim = SurfaceDimLight,
    primaryFixed = BluePrimaryLight,
    primaryFixedDim = BluePrimaryFixedDim,
    onPrimaryFixed = BluePrimaryDark,
    onPrimaryFixedVariant = BluePrimary,
    secondaryFixed = NeutralSecondaryContainer,
    secondaryFixedDim = NeutralSecondaryFixed,
    onSecondaryFixed = NeutralSecondaryDark,
    onSecondaryFixedVariant = NeutralSecondary,
    tertiaryFixed = PurpleTertiaryLight,
    tertiaryFixedDim = PurpleTertiaryFixedDim,
    onTertiaryFixed = PurpleTertiaryDark,
    onTertiaryFixedVariant = PurpleTertiary
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF58A6FF),
    onPrimary = BluePrimaryDark,
    primaryContainer = BluePrimaryFixedContainer,
    onPrimaryContainer = BluePrimaryLight,
    inversePrimary = BluePrimary,
    secondary = OutlineDark,
    onSecondary = OnBackgroundDark,
    secondaryContainer = NeutralSecondaryFixedContainer,
    onSecondaryContainer = NeutralSecondaryContainer,
    tertiary = PurpleTertiaryFixedDim,
    onTertiary = PurpleTertiaryDark,
    tertiaryContainer = PurpleTertiaryFixedContainer,
    onTertiaryContainer = PurpleTertiaryLight,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    surfaceTint = SurfaceTintDark,
    inverseSurface = InverseSurfaceDark,
    inverseOnSurface = InverseOnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark,
    scrim = Scrim,
    surfaceBright = SurfaceBrightDark,
    surfaceContainer = SurfaceContainerDark,
    surfaceContainerHigh = SurfaceContainerHighDark,
    surfaceContainerHighest = SurfaceContainerHighestDark,
    surfaceContainerLow = SurfaceContainerLowDark,
    surfaceContainerLowest = SurfaceContainerLowestDark,
    surfaceDim = SurfaceDimDark,
    primaryFixed = BluePrimaryFixedContainer,
    primaryFixedDim = BluePrimaryFixedContainerDark,
    onPrimaryFixed = BluePrimaryLight,
    onPrimaryFixedVariant = BluePrimaryInverse,
    secondaryFixed = NeutralSecondaryFixedContainer,
    secondaryFixedDim = NeutralSecondaryFixedDimContainer,
    onSecondaryFixed = NeutralSecondaryContainer,
    onSecondaryFixedVariant = NeutralSecondaryFixed,
    tertiaryFixed = PurpleTertiaryFixedContainer,
    tertiaryFixedDim = PurpleTertiaryFixedContainerDim,
    onTertiaryFixed = PurpleTertiaryLight,
    onTertiaryFixedVariant = PurpleTertiaryFixedDim
)

@Composable
fun RepoStarsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}