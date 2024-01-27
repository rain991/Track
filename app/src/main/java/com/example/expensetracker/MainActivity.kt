package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.viewmodels.LoginViewModel
import com.example.expensetracker.data.viewmodels.MainViewModel
import com.example.expensetracker.presentation.login.LoginScreen
import com.example.expensetracker.presentation.other.ScreenManager
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val expensesDao: ExpensesDAO by inject()
    private val expenseCategoryDao: ExpenseCategoryDao by inject()
    private val expensesListRepository: ExpensesListRepositoryImpl by inject()
    private val categoriesListRepository: CategoriesListRepositoryImpl by inject()
    private val dataStoreManager: DataStoreManager by inject()
    private val loginViewModel by viewModels<LoginViewModel>()
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch { // warning
            expensesListRepository.setExpensesList(expensesDao)
            categoriesListRepository.setCategoriesList(expenseCategoryDao)
        }

        runBlocking {
            withContext(Dispatchers.IO) {
                dataStoreManager.loginCountFlow.collect {
                    if (it == 0) {
                        mainViewModel.setMainScreenAvailable(false)
                    } else {
                        mainViewModel.setMainScreenAvailable(true)
                    }
                }
                if (categoriesListRepository.getCategoriesList().size == 0) {
                    categoriesListRepository.addDefaultCategories(this@MainActivity)
                }
            }
        }
        expensesListRepository.sortExpensesItemsDateDesc()

        setContent {
            AppTheme {
                val mainScreenAvailable = mainViewModel.mainScreenAvailable.collectAsState()
                if (!mainScreenAvailable.value) { // Добавити обмін данними між LoginViewModel и SettingsData
                    LoginScreen(loginViewModel, onPositiveLoginChanges = { mainViewModel.setMainScreenAvailable(true) })
                    DisposableEffect(Unit) {
                        onDispose {
                            CoroutineScope(Dispatchers.IO).launch {
                                //  dataStoreManager.saveSettings(settingsData)     WARNING ABOUT LOGIN COMPOSE DATA SAVING
                            }
                        }
                    }
                }
                if (mainScreenAvailable.value) {
                    ScreenManager()
                }

                // SimplifiedBottomSheet(isVisible = true, settingsData = settingsData)
            }
        }
    }
}



