package com.example.expensetracker.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.R
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.converters.extractAdditionalDateInformation
import com.example.expensetracker.data.converters.extractDayOfWeekFromDate
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import org.koin.compose.koinInject
import kotlin.math.absoluteValue

@Composable
fun ExpensesCardTypeSimple(expenseItem: ExpenseItem, expenseCategory: ExpenseCategory) {
    val dataStoreManager = koinInject<DataStoreManager>()
    var expanded by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    val currentCurrency = dataStoreManager.currencyFlow.collectAsState(initial = "USD")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .height(if (expanded) 150.dp else 100.dp)
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded }, shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 4.dp)
        ) { // column contains 2 separate rows, for typical and expanded content
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { // containing non-expanded content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top
                ) {// warning Modifier.fillMaxHeight()
                    Row(
                        horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .weight(1f).fillMaxWidth()
                            .padding(2.dp)
                    ) {
                        if (expenseItem.note.isNotEmpty()) {
                            noteCard(expenseItem = expenseItem)
                        } else {
                            categoryCard(category = expenseCategory)
                        }
                    }
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.Bottom) {
                        Text(text = extractDayOfWeekFromDate(date = expenseItem.date) + ", " + extractAdditionalDateInformation(date = expenseItem.date))
                    }
                }
                ExpenseValueCard(expenseItem = expenseItem, currentCurrencyName = currentCurrency.value, isExpanded = expanded)
            }
            // Expanded content
            AnimatedVisibility(visible = expanded, enter = slideInVertically {
                with(density) { -60.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.Bottom
            ) + fadeIn(
                initialAlpha = 0.3f
            ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                Text("Hello")
            }
        }
    }
}

@Composable
private fun ExpenseValueCard(expenseItem: ExpenseItem, currentCurrencyName: String, isExpanded: Boolean) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 12.dp, focusedElevation = 14.dp), modifier = Modifier.padding(4.dp)) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .size(if (isExpanded) 110.dp else 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (expenseItem.value.absoluteValue % 1.0 >= 0.001) expenseItem.value.toString() else expenseItem.value.toInt()
                    .toString(), style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 24.sp
                )
            )
            Text(text = currentCurrencyName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun categoryCard(category: ExpenseCategory) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        modifier = Modifier
            .height(28.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = category.note,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun noteCard(expenseItem: ExpenseItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp), modifier = Modifier
            .height(28.dp).absolutePadding(left=8.dp, right =8.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = if (expenseItem.note.length < 8) stringResource(R.string.note_exp_list, expenseItem.note) else expenseItem.note,
            style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center//, modifier = Modifier.weight(1f)
        )
        
    }
}
