package com.example.expensetracker.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Category(val id: Int, val name: String)

@Composable
fun CategorySelectionScreen() {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val categories = listOf(
        Category(1, "Еда"),
        Category(2, "Транспорт"),
        Category(3, "Жилье"),
        // и так далее
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _,_ ->
//                    offsetX += pan.translation.x
//                    offsetY += pan.translation.y
                }
            }
    ) {
        Column {
            // Отобразите ваши категории как прямоугольники
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(
                            x = with(LocalDensity.current) { offsetX.toDp() },
                            y = with(LocalDensity.current) { offsetY.toDp() }
                        )
                        .background(Color.Gray)
                        .clickable {
                            selectedCategory = category
                        }
                ) {
                    Text(
                        text = category.name,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // Добавьте UI для выбора категории
        if (selectedCategory != null) {
            CategorySelectionOverlay(
                onDismiss = { selectedCategory = null },
                selectedCategory = selectedCategory!!
            )
        }
    }
}

@Composable
fun CategorySelectionOverlay(
    onDismiss: () -> Unit,
    selectedCategory: Category
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.5f)
    ) {
        // Отобразите дополнительные детали по выбранной категории или другие элементы интерфейса
        Column {
            Text(
                text = "Выбрана категория: ${selectedCategory.name}",
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Закрыть")
            }
        }
    }
}

@Composable
fun ExpenseTrackerApp() {
    // Ваш код для создания основного интерфейса приложения
    // вызовите CategorySelectionScreen() или другие экраны
}

@Preview(showBackground = true)
@Composable
fun CategorySelectionScreenPreview() {
    ExpenseTrackerApp()
}
