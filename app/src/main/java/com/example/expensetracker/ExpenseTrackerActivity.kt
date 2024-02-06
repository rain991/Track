package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class ExpenseTrackerActivity : ComponentActivity() {
    private val expensesDao: ExpensesDAO by inject()
    private val expenseCategoryDao: ExpenseCategoryDao by inject()
    private val expensesListRepository: ExpensesListRepositoryImpl by inject()
    private val categoriesListRepository: CategoriesListRepositoryImpl by inject()
    private val dataStore: DataStoreManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val defferedFirstLogin = lifecycleScope.async { dataStore.loginCountFlow.firstOrNull() == 0 }
        runBlocking {
            val firstLoginStatus = defferedFirstLogin.await()
        }


        CoroutineScope(Dispatchers.IO).launch { // warning

            dataStore.incrementLoginCount()
            expensesListRepository.setExpensesList(expensesDao)
            categoriesListRepository.setCategoriesList(expenseCategoryDao)
            if (categoriesListRepository.getCategoriesList().size == 0) {
                categoriesListRepository.addDefaultCategories(this@ExpenseTrackerActivity)
            }
        }
        expensesListRepository.sortExpensesItemsDateDesc()




        setContent {
            AppTheme {
                Navigation(dataStore, firstLoginStatus)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }
}



