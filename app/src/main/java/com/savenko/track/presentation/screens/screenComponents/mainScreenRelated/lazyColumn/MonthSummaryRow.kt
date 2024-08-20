package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem


@Composable
inline fun <reified T : FinancialEntity> MonthSummaryRow(
    modifier: Modifier,
    summary: Float,
    quantity: Int,
    monthName: String,
    preferableCurrency : Currency
) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            DashedDivider()
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text(text = "$monthName summary", style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when (T::class) {
                    ExpenseItem::class ->{
                        "Total expenses : $quantity"
                    }
                    IncomeItem::class ->{
                        "Total incomes : $quantity"
                    }
                    else -> {"Total operations : $quantity"}
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Overall : $summary")
            Spacer(modifier = Modifier.height(2.dp))
            DashedDivider()
        }
    }
}


@Composable
fun DashedDivider(
    color: Color = Color.White,
    dashWidth: Dp = 4.dp,
    dashGap: Dp = 4.dp,
    thickness: Dp = 1.dp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        val configuration = LocalConfiguration.current
        val dashCount = configuration.screenWidthDp.dp.value.div(dashWidth.value + dashGap.value).toInt()
        repeat(dashCount) {
            Box(
                modifier = Modifier
                    .width(dashWidth)
                    .fillMaxHeight()
                    .background(color)
            )
            Spacer(modifier = Modifier.width(dashGap))
        }
    }
}

//@Preview
//@Composable
//fun prev() {
//    MonthSummaryRow(modifier = Modifier.fillMaxWidth())
//}