package com.example.track.presentation.components.mainScreen.additionalInfoCards

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreenInfoComposable() {
    val expenseAndIncomeLazyColumnViewModel = koinViewModel<ExpenseAndIncomeLazyColumnViewModel>()
    val isScrolledBelow = expenseAndIncomeLazyColumnViewModel.isScrolledBelow.collectAsState()
    AnimatedVisibility(visible = !isScrolledBelow.value) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {

            }
            Card(
                modifier = Modifier
                    .height(120.dp)
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp), shape = RoundedCornerShape(8.dp), colors = CardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {

            }
        }
    }
}