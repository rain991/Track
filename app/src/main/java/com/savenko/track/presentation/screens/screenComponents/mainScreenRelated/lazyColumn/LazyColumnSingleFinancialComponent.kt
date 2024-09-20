package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.components.financialItemCards.FinancialItemCardTypeSimple
import com.savenko.track.presentation.other.colors.parseColor
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

/**
 * LazyColumnSingleFinancialComponent is wrapper of FinancialCard (currently only FinancialCardTypeSimple supported)
 * which represents composable to show for single financial item in financials lazy column
 *
 * @param isExpanded is financial item card expanded
 * @param financialEntity current financial card entity
 * @param financialCategory current financial category
 * @param preferableCurrency user preferable currency
 * @param financialEntityMonthSummary month summary (total value) related to current financial entity
 * @param financialEntityMonthQuantity month summary (quantity) related to current financial entity
 * @param overallMonthSummary [MonthSummaryRow](com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn.MonthSummaryRowKt.MonthSummaryRow) to be shown if needed, uses [FinancialCardNotion](com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion)
 * @param containsMonthSummaryRow does current single financial component needs MonthSummaryRow to be shown
 * @param isExpenseLazyColumn is placed in expenses lazy column
 * @param isPreviousDayDifferent additional parameter for dates labels
 * @param isPreviousYearDifferent  is year label shown
 * @param isNextDayDifferent is day label shown
 * @param isNextMonthDifferent is month label shown
 * @param isLastInList is current single financial component last in list
 * @param onDeleteFinancial callback on deleting financial entity
 * @param onClick callback on clicking on single financial component
 */
@Composable
fun LazyColumnSingleFinancialComponent(
    isExpanded: Boolean,
    financialEntity: FinancialEntity,
    financialCategory: CategoryEntity,
    preferableCurrency: Currency,
    financialEntityMonthSummary: Float,
    financialEntityMonthQuantity: Int,
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
    val coroutineScope = rememberCoroutineScope()
    val categoryColor = parseColor(financialCategory.colorId)
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = { state ->
            if (state == SwipeToDismissBoxValue.EndToStart) {
                coroutineScope.launch {
                    delay(200.milliseconds)
                    onDeleteFinancial(financialEntity)
                }
                true
            } else {
                false
            }
        }
    )

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
        SwipeToDismissBox(state = swipeToDismissBoxState, backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(categoryColor)
                        .padding(12.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_financial_item_CD),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }) {
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
        }
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