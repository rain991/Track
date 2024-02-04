package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.viewmodels.LoginViewModel
import com.example.expensetracker.data.viewmodels.ScreenManagerViewModel
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {
    private val expensesDao: ExpensesDAO by inject()
    private val expenseCategoryDao: ExpenseCategoryDao by inject()
    private val expensesListRepository: ExpensesListRepositoryImpl by inject()
    private val categoriesListRepository: CategoriesListRepositoryImpl by inject()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val dataStore : DataStoreManager by inject()
    private val screenManagerViewModel =  ScreenManagerViewModel(dataStore)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenManagerViewModel = getViewModel<ScreenManagerViewModel>()

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//
//            }
//        }
        CoroutineScope(Dispatchers.IO).launch { // warning
            expensesListRepository.setExpensesList(expensesDao)
            categoriesListRepository.setCategoriesList(expenseCategoryDao)
            if (categoriesListRepository.getCategoriesList().size == 0) {
                categoriesListRepository.addDefaultCategories(this@MainActivity)
            }

        }
        expensesListRepository.sortExpensesItemsDateDesc()

        setContent {
            AppTheme {
                val mainScreenAvailable = screenManagerViewModel.mainScreenAvailable.collectAsState()
                Navigation(dataStore)
            }
        }
    }
}



