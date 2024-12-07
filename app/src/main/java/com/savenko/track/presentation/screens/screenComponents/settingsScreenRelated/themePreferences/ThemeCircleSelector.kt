package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.themePreferences


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleWithBorder(
    circleColor: Color,
    isBorderEnabled: Boolean,
    borderColor: Color,
    circleRadius: Int,
    onClick: () -> Unit
) {
    Box(modifier = Modifier
        .size(circleRadius.dp)
        .clickable { onClick() }) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = canvasWidth.coerceAtMost(canvasHeight) / 2
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2
            val borderWidth = circleRadius / 8
            drawCircle(color = circleColor, radius = radius, center = Offset(centerX, centerY))
            if (isBorderEnabled) {
                drawCircle(
                    color = borderColor,
                    style = Stroke(width = borderWidth.toFloat()),
                    radius = radius - borderWidth / 2,
                    center = Offset(centerX, centerY)
                )
            }
        }
    }
}


@Preview
@Composable
private fun Prev() {
    CircleWithBorder(
        circleColor = MaterialTheme.colorScheme.primary,
        isBorderEnabled = true,
        borderColor = MaterialTheme.colorScheme.onPrimary,
        circleRadius = 32,
        onClick = {

        }
    )
}