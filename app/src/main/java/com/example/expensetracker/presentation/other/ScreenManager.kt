package com.example.expensetracker.presentation.other

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenManager(expensesDAO: ExpensesDAO, expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> FirstScreen(expensesDAO = expensesDAO, expensesListRepositoryImpl = expensesListRepositoryImpl)
                1 -> SecondScreen(expensesDAO = expensesDAO, expensesListRepositoryImpl = expensesListRepositoryImpl)
                2 -> ThirdScreen(expensesDAO = expensesDAO, expensesListRepositoryImpl = expensesListRepositoryImpl)
            }
        }
    }
}


@Composable
fun Header(categoryName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth().height(48.dp).padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = categoryName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }

}