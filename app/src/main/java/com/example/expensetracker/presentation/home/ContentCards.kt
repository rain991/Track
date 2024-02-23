package com.example.expensetracker.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.converters.extractAdditionalDateInformation
import com.example.expensetracker.data.converters.extractDayOfWeekFromDate
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import org.koin.compose.koinInject
import kotlin.math.absoluteValue

@Composable
fun ExpensesCardTypeSimple(expenseItem: ExpenseItem) {
    val dataStoreManager = koinInject<DataStoreManager>()
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val currentCurrency = dataStoreManager.currencyFlow.collectAsState(initial = "USD")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }, shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) { // column contains 2 separate rows, for typical and expanded content
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxHeight()) {// warning Modifier.fillMaxHeight()
                    Text(text = if (expenseItem.note.length < 8) "Note: ${expenseItem.note}" else expenseItem.note)
                    Text(text = extractDayOfWeekFromDate(date = expenseItem.date) + "," + extractAdditionalDateInformation(date = expenseItem.date))
                }
                
                VerticalDivider()
                Column(modifier = Modifier.fillMaxHeight()){
                    Text(text = if(expenseItem.value.absoluteValue % 1.0 >= 0.005) expenseItem.value.toString() else expenseItem.value.toInt().toString())
                    Text(text = currentCurrency.value)
                }
            }
            // Expanded content
            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically {
                    with(density) { -20.dp.roundToPx() }
                } + expandVertically(
                    expandFrom = Alignment.Bottom
                ) + fadeIn(
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