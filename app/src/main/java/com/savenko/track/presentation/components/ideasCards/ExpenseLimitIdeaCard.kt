package com.savenko.track.presentation.components.ideasCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

/*  Contains Card used in expense screen feed to show expense limit entity  */
@Composable
fun ExpenseLimitIdeaCard(expenseLimit: ExpenseLimits, completedValue: Float, preferableCurrency: Currency) {
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepository>()
    val localContext = LocalContext.current
    var plannedText by remember { mutableStateOf(buildAnnotatedString { }) }
    var alreadySpentText by remember { mutableStateOf(buildAnnotatedString { }) }
    LaunchedEffect(preferableCurrency, expenseLimit.goal, completedValue) {
        plannedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    localContext.getString(R.string.planned)
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(expenseLimit.goal)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(expenseLimit.goal)
                    }
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    " " + preferableCurrency.ticker
                )
            }
        }
        alreadySpentText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    localContext.getString(R.string.already_spent_expense_limit_card)
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                )
            ) {
                append(
                    " " + if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(completedValue)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(completedValue)
                    }
                )
            }
            withStyle(
                style = SpanStyle(
                    fontSize = 16.sp
                )
            ) {
                append(
                    " " + preferableCurrency.ticker
                )
            }
        }
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp).scale(0.75f), horizontalArrangement = Arrangement.Center) {
            Card(colors = CardColors(
                containerColor = expenseLimitSpecificColor,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = expenseLimitSpecificColor,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            ),modifier = Modifier
            ) {
                Text(
                    text = stringResource(R.string.expense_limit),
                    style = MaterialTheme.typography.headlineSmall/*.copy(fontWeight = FontWeight.SemiBold)*/,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
        if (expenseLimit.isRelatedToAllCategories) Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 2.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            if (expenseLimit.isRelatedToAllCategories) {
                Text(text = plannedText)
                Spacer(Modifier.height(8.dp))
                Text(text = alreadySpentText)
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = plannedText, maxLines = 1)
                    Text(text = alreadySpentText, maxLines = 1)
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.padding(
                    start =
                    if (expenseLimit.isRelatedToAllCategories) {
                        0.dp
                    } else {
                        24.dp
                    }
                )
            ) {
                Text(
                    text = stringResource(R.string.categories_message_expense_limit_card) + if (expenseLimit.isRelatedToAllCategories) {
                        stringResource(R.string.related_to_all_categories_expense_limit_card)
                    } else {
                        ""
                    }
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                if (expenseLimit.firstRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory =
                                expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.firstRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategoryChip(
                                category = currentCategory!!,
                                borderColor = null,
                                isSelected = false,
                                chipScale = 0.8f,
                                onSelect = {})
                        }
                    }
                }
                if (expenseLimit.secondRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory =
                                expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.secondRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategoryChip(
                                category = currentCategory!!,
                                borderColor = null,
                                isSelected = false,
                                chipScale = 0.8f,
                                onSelect = {})
                        }
                    }
                }
                if (expenseLimit.thirdRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory =
                                expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.thirdRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategoryChip(
                                category = currentCategory!!,
                                borderColor = null,
                                isSelected = false,
                                chipScale = 0.8f,
                                onSelect = {})
                        }
                    }
                }
            }
        }
    }
}