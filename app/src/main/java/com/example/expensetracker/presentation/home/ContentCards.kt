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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
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
            .clickable { expanded = !expanded }, shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp)
        ) { // column contains 2 separate rows, for typical and expanded content
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center ) {// warning Modifier.fillMaxHeight()
                    if (expenseItem.note.isNotEmpty()) {
                        Text(text = if (expenseItem.note.length < 8) "Note: ${expenseItem.note}" else expenseItem.note)
                    }
                    Text(text = extractDayOfWeekFromDate(date = expenseItem.date) + ", " + extractAdditionalDateInformation(date = expenseItem.date))
                }
                ExpenseValueCard(expenseItem = expenseItem, currentCurrencyName = currentCurrency.value)
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

@Composable
private fun ExpenseValueCard(expenseItem: ExpenseItem, currentCurrencyName : String){
   Card(elevation = CardDefaults.cardElevation(defaultElevation = 2.dp, focusedElevation = 12.dp)){
       Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
           Text(
               text = if (expenseItem.value.absoluteValue % 1.0 >= 0.001) expenseItem.value.toString() else expenseItem.value.toInt()
                   .toString(),
               style = MaterialTheme.typography.bodyLarge.copy(
                   color = MaterialTheme.colorScheme.onPrimaryContainer,
                   fontSize = 24.sp
               )
           )
           Text(text = currentCurrencyName, style = MaterialTheme.typography.bodyMedium)
       }
   }

}
//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun Preview(){
//    val expenseCategory = ExpenseCategory(categoryId = 10, note = "CategoryName", colorId = Color.Red.value.toLong() )
//    val expenseItem = ExpenseItem(value = 500.5f, note = "My note", categoryId = expenseCategory.categoryId, date = convertLocalDateToDate(LocalDate.now()) )
//        ExpensesCardTypeSimple(expenseItem = expenseItem)
//}