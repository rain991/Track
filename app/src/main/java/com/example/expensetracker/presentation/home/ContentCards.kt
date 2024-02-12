package com.example.expensetracker.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.models.Expenses.ExpenseItem

@Composable
fun ExpensesCardTypeSimple(expenseItem: ExpenseItem) {
    var visible by remember { mutableStateOf(false) }
    val density = LocalDensity.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { visible = !visible }, shape = RoundedCornerShape(8.dp)
    ) { // Design to be implemented soon
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = expenseItem.note)
            Text(text = expenseItem.date.toString())
            Text(text = expenseItem.value.toString())

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -20.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the bottom.
                    expandFrom = Alignment.Bottom
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                Text(
                    "Hello",
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )
            }

        }
    }
}