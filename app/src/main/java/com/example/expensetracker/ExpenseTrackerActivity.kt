package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.viewmodels.UserDataViewModel
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class ExpenseTrackerActivity : ComponentActivity() {
    private val expenseItemsDao: ExpenseItemsDAO by inject()
    private val expenseCategoryDao: ExpenseCategoryDao by inject()
    private val expensesListRepository: ExpensesListRepositoryImpl by inject()
    private val categoriesListRepository: CategoriesListRepositoryImpl by inject()
    private val userDataViewModel: UserDataViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore : DataStoreManager by inject()
        Log.d("MyLog", "${userDataViewModel.currentUser}")
        CoroutineScope(Dispatchers.IO).launch { // warning
            dataStore.incrementLoginCount()
            expensesListRepository.setExpensesList(expenseItemsDao)
            categoriesListRepository.getCategoriesList(expenseCategoryDao)
            if (categoriesListRepository.getCategoriesList().isEmpty()) {
                categoriesListRepository.addDefaultCategories(this@ExpenseTrackerActivity)
            }
        }
        expensesListRepository.sortExpensesItemsDateDesc()

        setContent {
            AppTheme {
                Navigation(dataStore)
            }
        }
        Log.d("MyLog", "${userDataViewModel.currentUser}")
    }
}



