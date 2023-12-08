package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.presentation.AppTheme
import com.example.expensetracker.presentation.PagerTest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val expensesDAO = ExpensesDB.getInstance(applicationContext).dao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // WARNING expensesDAO was previously private lateinit var


//        lifecycleScope.launch {
//            expensesDAO.insertItem(testExpenseItem1)
//       }
        setContent {
            AppTheme {
                PagerTest()
            }

            }
        }
    }

