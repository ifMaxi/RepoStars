package com.maxidev.repostars.ui.theme

import androidx.compose.ui.graphics.Brush

val backgroundGradientLight = Brush.linearGradient(
    colors = listOf(
        BackgroundLight,
        SurfaceBrightLight,
        SurfaceContainerHighestLight,
        BluePrimaryLight,
        PurpleTertiaryLight,
        SurfaceVariantLight,
        NeutralSecondaryContainer
    )
)
val backgroundGradientDark = Brush.linearGradient(
    colors = listOf(
        BackgroundDark,
        SurfaceContainerLowestDark,
        SurfaceContainerLowDark,
        BluePrimaryDark,
        SurfaceVariantDark,
        PurpleTertiaryFixedContainerDim,
        NeutralSecondaryFixedDimContainer
    )
)