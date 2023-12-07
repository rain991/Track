package com.example.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.presentation.AppTheme
import com.example.expensetracker.presentation.PagerTest

class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO : ExpensesDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao
//        lifecycleScope.launch {
//            expensesDAO.insertItem(testExpenseItem1)
//        }
        setContent {
            AppTheme {
                PagerTest()
            }

            }
        }
    }

