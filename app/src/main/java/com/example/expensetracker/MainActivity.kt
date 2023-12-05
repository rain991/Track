package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.visualisationexpensestracker.ui.theme.PagerTest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO : ExpensesDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao
//        lifecycleScope.launch {
//            expensesDAO.insertItem(testExpenseItem1)
//        }
        setContent {
            PagerTest()
            }
        }
    }

