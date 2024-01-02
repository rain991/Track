package com.example.expensetracker.presentation

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.data.ExpenseItem
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import java.time.LocalDate
import java.time.format.DateTimeFormatter
@Composable
fun MainInfoComposable(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp), shape = RoundedCornerShape(8.dp)
    ) {
            PieCategoriesChart()
    }
}


@Composable
fun ExpensesLazyColumn(expenses: MutableList<ExpenseItem>) {
    val listState = rememberLazyListState()
    var isScrollUpButtonNeeded by remember { mutableStateOf(false) }
    var isScrollingUp by remember { mutableStateOf(false) }

    Box {
        if (isScrollUpButtonNeeded) {
            Box(modifier = Modifier
                .align(Alignment.TopEnd)
                .zIndex(1f)) {
                FloatingActionButton(
                    onClick = { isScrollingUp = true }, modifier = Modifier
                        .size(52.dp)
                        .padding(top = 12.dp, end = 16.dp),
                    shape = RoundedCornerShape(95),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
                }
            }
        }
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(expenses.size-1) { index ->   //  expenses.size-1 warning
                val expense = expenses[index]
                var isPreviousDayDifferent = index == 0
                var isNextDayDifferent = false
                var isDifferentMonth = false
                if (index > 0 && index < expenses.size - 1) {
                    isPreviousDayDifferent = !areDatesEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
                    isNextDayDifferent = !areDatesEqual(parseStringToDate(expenses[index + 1].date), parseStringToDate(expense.date))
                    isDifferentMonth = !areMonthsEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
                }

                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    if (isScrollingUp) LaunchedEffect(listState) {
                        listState.animateScrollToItem(index = 0)
                        isScrollingUp = false
                    }
                    isScrollUpButtonNeeded = index > 4
                    Column {
                        if (index == 0) {
                            Row(modifier = Modifier.fillMaxWidth()){
                                Transactions()
                                Spacer(modifier = Modifier.width(12.dp))
                                CustomTabSample()
                            }

                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        if (isDifferentMonth) {
                            ExpenseMonthHeader(parseStringToDate(expense.date))
                        }
                        if (isPreviousDayDifferent) {
                            ExpenseDayHeader(parseStringToDate(expense.date))
                            // Spacer(modifier = Modifier.height(4.dp))
                        }

                        ExpensesCardTypeSimple(expenseItem = expense)
                        if (isNextDayDifferent) Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}

@Composable
private fun Transactions() {
    Text(text = "Transactions", style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun ExpenseDayHeader(localDate: LocalDate) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(text = "${localDate.dayOfMonth}.", style = MaterialTheme.typography.titleMedium)
        Text(text = "${localDate.month.value}", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun ExpenseMonthHeader(localDate: LocalDate) {
    val monthResId = getMonthResID(localDate)
    val month = stringResource(id = monthResId)
    Box() {
        Text(text = month, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
private fun UpButton(onClick: () -> Unit) {
    Box() {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(16.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            )
        ) {
            Icons.Default.KeyboardArrowUp
        }
    }
}


@Composable
fun PieCategoriesChart(){
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.main_screen_pie_chart, null, false)
            val aaChartView = view.findViewById<AAChartView>(R.id.aa_chart_view)
            //Creating chart model
            val aaChartModel : AAChartModel = AAChartModel()
                .chartType(AAChartType.Area)
                .title("title")
                .subtitle("subtitle")
                .backgroundColor("#4b2b7f")
                .dataLabelsEnabled(true)//.animationType(AAChartAnimationType. EaseInCubic).animationDuration(400)
                .series(arrayOf(
                    AASeriesElement()
                        .name("Tokyo")
                        .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6)),
                    AASeriesElement()
                        .name("NewYork")
                        .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
                    AASeriesElement()
                        .name("London")
                        .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
                    AASeriesElement()
                        .name("Berlin")
                        .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
                )
                )
            aaChartView.aa_drawChartWithChartModel(aaChartModel)

            view // return the view
        }, modifier = Modifier.fillMaxSize(),
        update = { view ->
            // Update the view
        }
    )
}


fun getMonthResID(localDate: LocalDate): Int {
    val monthResId = when (localDate.monthValue) {
        1 -> R.string.january
        2 -> R.string.february
        3 -> R.string.march
        4 -> R.string.april
        5 -> R.string.may
        6 -> R.string.june
        7 -> R.string.july
        8 -> R.string.august
        9 -> R.string.september
        10 -> R.string.october
        11 -> R.string.november
        12 -> R.string.december
        else -> R.string.unknown_month
    }
    return monthResId
}

fun parseStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(dateString, formatter)
}

private fun areDatesEqual(date1: LocalDate, date2: LocalDate): Boolean { // year, month and day is same in dates
    return (date1.isEqual(date2))
}

private fun areMonthsEqual(date1: LocalDate, date2: LocalDate): Boolean {  // year, month same
    return (date1.month == date2.month && date1.year == date2.year)
}

private fun areYearsEqual(date1: LocalDate, date2: LocalDate): Boolean {  // year is same
    return (date1.year == date2.year)
}