package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.DataStoreManager
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO: ExpensesDAO
    private val loginViewModel by viewModels<LoginViewModel>()   // probably should be private later
    private val settingsData = SettingsData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao
        val dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch(Dispatchers.IO) {
            ExpensesListRepositoryImpl.setExpensesList(expensesDAO)
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

        for (i in 1..50) {
            ExpensesListRepositoryImpl.addExpensesItem(ExpensesListRepositoryImpl.generateRandomExpenseObject())
        }
        setContent {
            AppTheme {
                val firstLogin = settingsData.getLoginCount() == 0
                var mainScreenAvailable by remember { mutableStateOf(settingsData.getLoginCount() > 0) }
                if (firstLogin) {
                    LoginScreen(loginViewModel, onPositiveLoginChanges = { newMainScreenAvailable ->
                        mainScreenAvailable = newMainScreenAvailable
                        settingsData.setLoginCount(settingsData.getLoginCount() + 1)
                    })
                    DisposableEffect(Unit) {
                        onDispose {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStoreManager.saveSettings(settingsData)
                            }
                        }
                    }
                }
                if (mainScreenAvailable) {
                    PagerTest(expensesDAO = expensesDAO)
                }

                // val booleanValue by booleanFlow.collectAsState(initial = false) WILL BE USED FOR UI SETTINGS
            }
        }
    }
}

