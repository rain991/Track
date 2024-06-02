package com.example.track.presentation.components.settingsScreen.components



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CircleWithBorder(
    circleColor: Color,
    isBorderEnabled : Boolean,
    borderColor: Color,
    borderWidth: Float,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val radius = canvasWidth.coerceAtMost(canvasHeight) / 2
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2
            drawCircle(color = circleColor, radius = radius, center = Offset(centerX, centerY))
            if(isBorderEnabled){
                drawCircle(
                    color = borderColor,
                    style = Stroke(width = borderWidth),
                    radius = radius - borderWidth / 2,
                    center = Offset(centerX, centerY)
                )
            }
        }
    }
}


@Preview
@Composable
private fun prev() {
    CircleWithBorder(circleColor = MaterialTheme.colorScheme.primary, isBorderEnabled = true, borderColor = MaterialTheme.colorScheme.onPrimary,borderWidth = 23.0f )
}