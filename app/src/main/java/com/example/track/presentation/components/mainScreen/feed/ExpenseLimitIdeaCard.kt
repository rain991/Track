package com.example.track.presentation.components.mainScreen.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Text
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.common.ui.CategoryChip
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun ExpenseLimitIdeaCard(expenseLimit: ExpenseLimits) {
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepositoryImpl>()
    var completedAbsoluteValue by remember { mutableFloatStateOf(0.0f) }
    var complitionRate by remember { mutableFloatStateOf(0.0f) }
    LaunchedEffect(key1 = mainScreenFeedViewModel.ideaList) {
        completedAbsoluteValue = mainScreenFeedViewModel.getCompletionValue(expenseLimit).value
        mainScreenFeedViewModel.getCompletionValue(expenseLimit).collect {
            if (it != 0.0f) {
                complitionRate = expenseLimit.goal.div(it)
            } else 0.0f
        }
    }
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Expense limit",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(4.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.Start) {
                Text(text = "Planned")
                Spacer(Modifier.width(2.dp))
                Text(text = "${expenseLimit.goal}")
            }
            if (expenseLimit.isRelatedToAllCategories) {
                Text(text = "all categories limit")
            } else {
                Text(text = "for selected categories:")
                Row(modifier = Modifier.wrapContentWidth()) {
                    if (expenseLimit.firstRelatedCategoryId != null) CategoryChip(
                        category = expenseCategoriesListRepositoryImpl.getCategoryById(
                            expenseLimit.firstRelatedCategoryId
                        ), isSelected = false, onSelect = {})
                    if (expenseLimit.secondRelatedCategoryId != null) CategoryChip(
                        category = expenseCategoriesListRepositoryImpl.getCategoryById(
                            expenseLimit.secondRelatedCategoryId
                        ), isSelected = false, onSelect = {})
                    if (expenseLimit.thirdRelatedCategoryId != null) CategoryChip(
                        category = expenseCategoriesListRepositoryImpl.getCategoryById(
                            expenseLimit.thirdRelatedCategoryId
                        ), isSelected = false, onSelect = {})
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                Text(text = "Completed for")
                Spacer(Modifier.width(2.dp))
                Text(text = completedAbsoluteValue.toString())
            }
        }
    }
}