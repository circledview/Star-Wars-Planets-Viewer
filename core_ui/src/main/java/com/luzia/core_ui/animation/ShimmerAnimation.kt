package com.luzia.core_ui.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    gradientWidth: Dp = 16.dp,
    shape: CornerBasedShape = RoundedCornerShape(4.dp),
    shimmerColor: Color = Color(0xFFD2D2D2),
    backColor: Color = Color(0xFFA2A2A2)
) {
    val density = LocalDensity.current
    val widthPx = with(density) { width.toPx() }
    val heightPx = with(density) { height.toPx() }
    val gWidth = with(density) { gradientWidth.toPx() }

    val transition = rememberInfiniteTransition(label = "shimmer")
    val xShimmer by transition.animateFloat(
        initialValue = -gWidth,
        targetValue = widthPx + gWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "xShimmer"
    )

    val brush = Brush.linearGradient(
        colors = listOf(backColor, shimmerColor, backColor),
        start = Offset(xShimmer - gWidth, 0f),
        end = Offset(xShimmer, heightPx)
    )

    Spacer(
        modifier = modifier
            .size(width, height)
            .background(brush, shape)
    )
}
