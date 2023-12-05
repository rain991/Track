package com.example.expensetracker.presentation

import android.provider.CalendarContract
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun WoofTheme(darkTheme: Boolean = false) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography(
            fontFamily = FontFamily. Default,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        shapes = Shapes(
            cornerRadius = 4.dp,
            elevation = 4.dp,
        ),
    ) {
        // Your app content here
    }
}

class MaterialTheme(colors: Any, typography: Any, shapes: Any, function: () -> Unit) {

}

object DarkColors : Colors() {
    override fun backgroundColor(): Color = Color.Black
    override fun primaryColor(): Color = Color.Blue
    override fun secondaryColor(): Color = Color.Green
}

object LightColors : Colors() {
    override fun backgroundColor(): Color = Color.White
    override fun primaryColor(): Color = Color.Red
    override fun secondaryColor(): Color = Color.Yellow
}