package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.ExpenseCategoryDao
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.data.LoginViewModel
import com.example.expensetracker.data.SettingsData
import com.example.expensetracker.presentation.LoginScreen
import com.example.expensetracker.presentation.PagerTest
import com.example.expensetracker.presentation.themes.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    val expensesDao: ExpensesDAO by inject()
    val expensesListRepository: ExpensesListRepositoryImpl by inject()
    private lateinit var expenseCategoryDao: ExpenseCategoryDao
    private val loginViewModel by viewModels<LoginViewModel>()
    private val settingsData = SettingsData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val expensesDB = ExpensesDB.getInstance(applicationContext)
//        expensesDao = expensesDB.dao
        expenseCategoryDao = expensesDB.categoryDao
        val dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch(Dispatchers.IO) {
            expensesListRepository.setExpensesList(expensesDao)
        }
        runBlocking {
            val pref = dataStoreManager.getSettings().first()
            withContext(Dispatchers.IO) {
                settingsData.setSettings(
                    currency = pref.getCurrency(),
                    budget = pref.getBudget(),
                    name = pref.getName(),
                    loginCount = pref.getLoginCount()
                )
                Log.d("MyLog", "Login counter: ${settingsData.getLoginCount()}")
                if (settingsData.getLoginCount() != 0) settingsData.setLoginCount(settingsData.getLoginCount() + 1)
                dataStoreManager.saveSettings(settingsData)
            }
        }

//        for (i in 1..50) {
//            expensesListRepository.addExpensesItem(ExpensesListRepositoryImpl.generateRandomExpenseObject())
//        }
        expensesListRepository.sortExpensesItemsDesc()
        setContent {
            AppTheme {
                var mainScreenAvailable by remember { mutableStateOf(settingsData.getLoginCount() > 0) }
                if (!mainScreenAvailable) { // Добавити обмін данними між LoginViewModel и SettingsData
                    LoginScreen(loginViewModel, onPositiveLoginChanges = { newMainScreenAvailable ->
                        mainScreenAvailable = newMainScreenAvailable
                        //settingsData.setLoginCount(settingsData.getLoginCount() + 1)
                    })
                    DisposableEffect(Unit) {
                        onDispose {
                            CoroutineScope(Dispatchers.IO).launch {
//                                Log.d("MyLog", "Dispose Name: ${settingsData.getName()}")
//                                Log.d("MyLog", "Dispose Currency: ${settingsData.getCurrency()}")
//                                Log.d("MyLog", "Dispose Budget: ${settingsData.getBudget()}")
                                dataStoreManager.saveSettings(settingsData)
                            }
                        }
                    }
                }
                if (mainScreenAvailable) {
                    if (settingsData.getLoginCount() == 0) {
                        settingsData.setSettings(
                            currency = loginViewModel.currency!!.ticker,
                            budget = loginViewModel.income!!.toFloat(),
                            name = loginViewModel.firstName!!,
                            loginCount = (settingsData.getLoginCount() + 1)
                        )
                        LaunchedEffect(Unit) {
                            withContext(Dispatchers.IO) {
                                dataStoreManager.saveSettings(settingsData)
                            }
                        }
                    }
//                    Log.d( "MyLog",
//                        "mainScreenAvailable Login counter: ${settingsData.getLoginCount()}" )
//                    Log.d("MyLog", "mainScreenAvailable Name: ${settingsData.getName()}")
//                    Log.d("MyLog", "mainScreenAvailable Currency: ${settingsData.getCurrency()}")
//                    Log.d("MyLog", "mainScreenAvailable Budget: ${settingsData.getBudget()}")
//                    Log.d(
//                        "MyLog", "mainScreenAvailable Viewmodel Name: ${loginViewModel.firstName}"
//                    )
//                    Log.d(
//                        "MyLog",
//                        "mainScreenAvailable Viewmodel Currency: ${loginViewModel.currency?.ticker}"
//                    )
//                    Log.d("MyLog", "mainScreenAvailable Viewmodel Budget: ${loginViewModel.income}")

                    PagerTest(expensesDAO = expensesDao, expensesListRepositoryImpl = expensesListRepository)

                   // SimplifiedBottomSheet(isVisible = true, onDismiss = { /*TODO*/ }, expensesDao = expensesDao)
                }
                // val booleanValue by booleanFlow.collectAsState(initial = false) WILL BE USED FOR UI SETTINGS
            }
        }
    }
}


