package com.example.expensetracker.presentation.common

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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { // containing non-expanded content
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {// warning Modifier.fillMaxHeight()
                    Row(
                        horizontalArrangement = Arrangement.Center, modifier = Modifier
                            .wrapContentHeight() //weight 1f
                            .fillMaxWidth()
                    ) {
                        if (expenseItem.note.isNotEmpty()) {
                            noteCard(expenseItem = expenseItem)
                        } else {
                            categoryCard(category = expenseCategory)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.padding(4.dp), verticalAlignment = Alignment.Bottom) {
                        Text(text = extractDayOfWeekFromDate(date = expenseItem.date) + ", " + extractAdditionalDateInformation(date = expenseItem.date))
                    }
                    if(expanded){
                        Spacer(modifier = Modifier.weight(0.7f))
                    }

                    Row(modifier = Modifier
                        .wrapContentWidth()
                    ) { // Expanded content
                        AnimatedVisibility(visible = expanded, enter = slideInVertically {
                            with(density) { -60.dp.roundToPx() }
                        } + expandVertically(
                            expandFrom = Alignment.Bottom
                        ) + fadeIn(
                            initialAlpha = 0.3f
                        ), exit = slideOutVertically() + shrinkVertically() + fadeOut()) {
                            Text("Text")
                        }
                    }
                    if(expanded){
                        Spacer(modifier = Modifier.weight(1f))
                    }



                }
                ExpenseValueCard(expenseItem = expenseItem, currentCurrencyName = currentCurrency.value, isExpanded = expanded)



            } // non-expanded ends


        }
    }
}

@Composable
private fun ExpenseValueCard(expenseItem: ExpenseItem, currentCurrencyName: String, isExpanded: Boolean) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 22.dp, focusedElevation = 14.dp), modifier = Modifier.padding(4.dp)) {
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
        modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ), shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(
                text = category.note,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
private fun noteCard(expenseItem: ExpenseItem) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp), modifier = Modifier.wrapContentSize(),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        ), shape = MaterialTheme.shapes.small
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            Text(
                text = if (expenseItem.note.length < 8) stringResource(R.string.note_exp_list, expenseItem.note) else expenseItem.note,
                style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center
            )
        }
    }
}
