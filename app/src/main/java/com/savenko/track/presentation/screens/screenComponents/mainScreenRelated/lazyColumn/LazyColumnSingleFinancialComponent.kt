package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.components.financialItemCards.FinancialItemCardTypeSimple
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import java.util.Calendar
import java.util.Locale


/**
 * LazyColumnSingleFinancialComponent is wrapper of FinancialCard (currently only FinancialCardTypeSimple supported)
 * which represents composable to show for single financial item in financials lazy column
 */
@Composable
fun LazyColumnSingleFinancialComponent(
    isExpanded: Boolean,
    financialEntity: FinancialEntity,
    financialCategory: CategoryEntity,
    preferableCurrency: Currency,
    financialEntityMonthSummary : Float,
    financialEntityMonthQuantity : Int,
    overallMonthSummary: FinancialCardNotion?,
    containsMonthSummaryRow: Boolean,
    isExpenseLazyColumn: Boolean,
    isPreviousDayDifferent: Boolean,
    isPreviousYearDifferent: Boolean,
    isNextDayDifferent: Boolean,
    isNextMonthDifferent: Boolean,
    isLastInList: Boolean,
    onDeleteFinancial: (FinancialEntity) -> Unit,
    onClick: (FinancialEntity) -> Unit
) {
    val locale = Locale.getDefault()
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            if (isPreviousDayDifferent) {
                if (isPreviousYearDifferent) {
                    FinancialYearLabel(
                        localDate = convertDateToLocalDate(
                            financialEntity.date
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))
                FinancialMonthLabel(
                    convertDateToLocalDate(
                        financialEntity.date
                    )
                )
                Text(
                    text = ", ",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                FinancialDayLabel(
                    localDate = convertDateToLocalDate(
                        financialEntity.date
                    ),
                    isPastSmallMarkupNeeded = false
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
        FinancialItemCardTypeSimple(
            financialEntity = financialEntity,
            categoryEntity = financialCategory,
            expanded = isExpanded,
            preferableCurrency = preferableCurrency,
            financialEntityMonthSummary = financialEntityMonthSummary,
            countOfFinancialEntities = financialEntityMonthQuantity,
            onDeleteFinancial = {
                onDeleteFinancial(financialEntity)
            },
            onClick = {
                onClick(financialEntity)
            }
        )
        if (isNextDayDifferent && !isNextMonthDifferent) Spacer(
            modifier = Modifier.height(
                20.dp
            )
        )
        if (containsMonthSummaryRow) {
            val calendar = Calendar.getInstance().apply { time = financialEntity.date }
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
            MonthSummaryRow(
                modifier = Modifier.fillMaxWidth(),
                summary = overallMonthSummary?.financialSummary ?: 0.0f,
                quantity = overallMonthSummary?.financialsQuantity ?: 0,
                monthName = monthName ?: "",
                preferableCurrency = preferableCurrency,
                financialTypes = if (isExpenseLazyColumn) {
                    FinancialTypes.Expense
                } else {
                    FinancialTypes.Income
                }
            )
        }
        if (isLastInList) {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}