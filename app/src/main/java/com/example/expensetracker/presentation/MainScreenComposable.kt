package com.example.expensetracker.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.domain.ExpenseItem
import com.example.visualisationexpensestracker.Presentation.FirstScreen
import com.example.visualisationexpensestracker.Presentation.SecondScreen
import com.example.visualisationexpensestracker.Presentation.ThirdScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTest(expensesDAO: ExpensesDAO) {
    val pagerState = rememberPagerState(initialPage = 1) { 3 }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> FirstScreen(expensesDAO = expensesDAO)
                1 -> SecondScreen(expensesDAO = expensesDAO)
                2 -> ThirdScreen(expensesDAO = expensesDAO)
            }
        }
    }
}

@Composable
fun Header(categoryName: String, isMenuButton: Boolean = true, isSearchButton: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isMenuButton) {    //TO be changed to settings
            IconButton(onClick = { /* Обработка нажатия на кнопку меню */ }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }

        Text(
            text = categoryName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

        if (isSearchButton) {  //To be changed to statistics
            IconButton(onClick = { /* Обработка нажатия на кнопку поиска */ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    }
}