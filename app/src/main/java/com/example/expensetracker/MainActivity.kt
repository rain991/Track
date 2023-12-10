package com.example.expensetracker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.presentation.AppTheme
import com.example.expensetracker.presentation.PagerTest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO: ExpensesDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao

        coroutineScope {
            launch {  ExpensesListRepositoryImpl.setExpensesList(expensesDAO)
        }}


        Log.d("MyLog", "${ExpensesListRepositoryImpl.getExpensesList()}")
        setContent {
            AppTheme {
                PagerTest(expensesDAO)
            }

        }
    }
}

