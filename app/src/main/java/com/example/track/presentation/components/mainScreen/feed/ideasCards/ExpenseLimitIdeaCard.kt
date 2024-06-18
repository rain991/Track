package com.example.track.presentation.components.mainScreen.feed.ideasCards

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.other.constants.CRYPTO_DECIMAL_FORMAT
import com.example.track.data.other.constants.FIAT_DECIMAL_FORMAT
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.currency.CurrencyTypes
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.presentation.components.screenComponents.additional.CategorySettingsChip
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

/*  Contains Card used in expense screen feed to show expense limit entity  */
@Composable
fun ExpenseLimitIdeaCard(expenseLimit: ExpenseLimits, completedValue: Float, preferableCurrency: Currency) {
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepositoryImpl>()
    val localContext = LocalContext.current
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.expense_limit),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 2.dp), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            var plannedText by remember { mutableStateOf("") }
            var alreadySpentText by remember { mutableStateOf("") }
            LaunchedEffect(preferableCurrency, expenseLimit.goal, completedValue) {
                plannedText = localContext.getString(
                    R.string.planned_value_expense_limit_card, if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(expenseLimit.goal)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(expenseLimit.goal)
                    }, preferableCurrency.ticker
                )

                alreadySpentText = localContext.getString(
                    R.string.already_spent_expense_limit_card, if (preferableCurrency.type == CurrencyTypes.FIAT) {
                        FIAT_DECIMAL_FORMAT.format(completedValue)
                    } else {
                        CRYPTO_DECIMAL_FORMAT.format(completedValue)
                    }, preferableCurrency.ticker
                )
            }

            if (expenseLimit.isRelatedToAllCategories) {
                Text(text = plannedText)
                Spacer(Modifier.height(8.dp))
                Text(text = alreadySpentText)
            } else {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Text(text = plannedText)
                    Text(text = alreadySpentText)
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
                    .padding(bottom = 4.dp)
            ) {
                if (expenseLimit.firstRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory = expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.firstRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategorySettingsChip(category = currentCategory!!) { }
                        }
                    }
                }
                if (expenseLimit.secondRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory = expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.secondRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategorySettingsChip(category = currentCategory!!) { }
                        }
                    }
                }
                if (expenseLimit.thirdRelatedCategoryId != null) {
                    var currentCategory by remember { mutableStateOf<ExpenseCategory?>(null) }
                    LaunchedEffect(key1 = Unit) {
                        withContext(Dispatchers.IO) {
                            currentCategory = expenseCategoriesListRepositoryImpl.getCategoryById(expenseLimit.thirdRelatedCategoryId)
                        }
                    }
                    if (currentCategory != null) {
                        Box(modifier = Modifier.weight(0.3f)) {
                            CategorySettingsChip(category = currentCategory!!) { }
                        }
                    }
                }
            }
        }
    }
}