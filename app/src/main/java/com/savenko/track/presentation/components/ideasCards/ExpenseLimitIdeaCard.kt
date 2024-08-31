package com.savenko.track.presentation.components.ideasCards

/*  Contains Card used in expense screen feed to show expense limit entity  */
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.R
import com.savenko.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.savenko.track.data.other.constants.expenseLimitSpecificColor
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.themes.purpleGreyTheme.purpleGreyNew_DarkColorScheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
fun ExpenseLimitIdeaCard(expenseLimit: ExpenseLimits, completedValue: Float, preferableCurrency: Currency) {
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepository>()
    val localContext = LocalContext.current
    var plannedText by remember { mutableStateOf(buildAnnotatedString { }) }
    var alreadySpentText by remember { mutableStateOf(buildAnnotatedString { }) }
    val listOfRelatedCategories = remember { mutableStateListOf<ExpenseCategory>() }

    LaunchedEffect(preferableCurrency, expenseLimit.goal, completedValue) {
        plannedText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 16.sp)) {
                append(localContext.getString(R.string.planned))
            }
            withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) {
                append(" " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                    FIAT_DECIMAL_FORMAT.format(expenseLimit.goal)
                } else {
                    CRYPTO_DECIMAL_FORMAT.format(expenseLimit.goal)
                })
            }
            withStyle(style = SpanStyle(fontSize = 14.sp)) {
                append(" " + preferableCurrency.ticker)
            }
        }
        alreadySpentText = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 16.sp)) {
                append(localContext.getString(R.string.already_spent_expense_limit_card))
            }
            withStyle(style = SpanStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)) {
                append(" " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                    FIAT_DECIMAL_FORMAT.format(completedValue)
                } else {
                    CRYPTO_DECIMAL_FORMAT.format(completedValue)
                })
            }
            withStyle(style = SpanStyle(fontSize = 14.sp)) {
                append(" " + preferableCurrency.ticker)
            }
        }
    }

    LaunchedEffect(expenseLimit) {
        val categories = withContext(Dispatchers.IO) {
            listOfNotNull(
                expenseLimit.firstRelatedCategoryId?.let { expenseCategoriesListRepositoryImpl.getCategoryById(it) },
                expenseLimit.secondRelatedCategoryId?.let { expenseCategoriesListRepositoryImpl.getCategoryById(it) },
                expenseLimit.thirdRelatedCategoryId?.let { expenseCategoriesListRepositoryImpl.getCategoryById(it) }
            )
        }
        listOfRelatedCategories.clear()
        listOfRelatedCategories.addAll(categories)
    }

    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp) , border = BorderStroke((1.2).dp, expenseLimitSpecificColor
    ), shape = RoundedCornerShape(16.dp)
    ) {
        Canvas(modifier = Modifier.wrapContentSize()) {
            val arcSize = 70.dp.toPx()
            drawArc(
                color = expenseLimitSpecificColor,
                startAngle = 0f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = androidx.compose.ui.geometry.Offset(-(arcSize / 2), -(arcSize / 2)),
                size = androidx.compose.ui.geometry.Size(arcSize, arcSize),
                style = Fill
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .scale(0.75f), horizontalArrangement = Arrangement.Center
        ) {
            Card(
                colors = androidx.compose.material3.CardDefaults.cardColors(
                    containerColor = expenseLimitSpecificColor,
                    contentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant,
                    disabledContainerColor = expenseLimitSpecificColor,
                    disabledContentColor = purpleGreyNew_DarkColorScheme.onSurfaceVariant
                ), modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.expense_limit),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.W500),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        if (expenseLimit.isRelatedToAllCategories) Spacer(Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp), verticalArrangement = Arrangement.SpaceAround
        ) {
            if (expenseLimit.isRelatedToAllCategories) {
                RelatedToAllCategoriesCardContent(plannedText = plannedText, alreadySpentText = alreadySpentText)
            } else {
                SpecifiedCategoriesCardContent(
                    listOfRelatedCategories = listOfRelatedCategories,
                    plannedText = plannedText,
                    alreadySpentText = alreadySpentText
                )
            }
        }
    }
}

@Composable
private fun RelatedToAllCategoriesCardContent(
    plannedText: AnnotatedString,
    alreadySpentText: AnnotatedString
) {
    Text(text = plannedText)
    Text(text = alreadySpentText)
    Text(
        text = stringResource(R.string.categories_message_expense_limit_card) + stringResource(R.string.related_to_all_categories_expense_limit_card)
    )
}

@Composable
private fun SpecifiedCategoriesCardContent(
    listOfRelatedCategories: List<ExpenseCategory>,
    plannedText: AnnotatedString,
    alreadySpentText: AnnotatedString
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        Text(text = plannedText, maxLines = 1)
        Text(text = alreadySpentText, maxLines = 1)
    }
    Spacer(Modifier.height(4.dp))
    Row(modifier = Modifier.padding(start = 24.dp)) {
        Text(text = stringResource(R.string.categories_message_expense_limit_card))
    }
    Spacer(modifier = Modifier.height(4.dp))
    Row(
        modifier = Modifier
            .wrapContentHeight()
    ) {
        listOfRelatedCategories.forEach { expenseCategory ->
            CategoryChip(
                category = expenseCategory,
                borderColor = null,
                isSelected = false,
                chipScale = 0.85f,
                onSelect = {})
        }
    }
}
