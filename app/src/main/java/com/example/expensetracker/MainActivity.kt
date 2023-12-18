package com.example.expensetracker


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import com.example.expensetracker.data.SettingsData
import com.example.expensetracker.presentation.DateTimePicker
import com.example.expensetracker.presentation.DateTimeSample2
import com.example.expensetracker.presentation.LoginScreen
import com.example.expensetracker.presentation.themes.AppTheme
import com.example.expensetracker.presentation.PagerTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    private lateinit var expensesDAO: ExpensesDAO

    // temporarily created variable for logincomposable
    private lateinit var datePicker: LocalDate

    private val settingsData = SettingsData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expensesDAO = ExpensesDB.getInstance(applicationContext).dao
        val dataStoreManager = DataStoreManager(this)
        lifecycleScope.launch(Dispatchers.IO) {
            ExpensesListRepositoryImpl.setExpensesList(expensesDAO)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val pref = dataStoreManager.getSettings().first()

            withContext(Dispatchers.Main) {
                settingsData.setSettings(
                    currency = pref.getCurrency(),
                    budget = pref.getBudget(),
                    name = pref.getName(),
                    loginCount = pref.getLoginCount()
                )

                Log.d("MyLog", "${settingsData.getLoginCount()}")
                settingsData.setLoginCount(settingsData.getLoginCount() + 1)
                Log.d("MyLog", "${settingsData.getLoginCount()}")
                dataStoreManager.saveSettings(settingsData)
            }
        }
        setContent {
            AppTheme {
                //PagerTest(expensesDAO)

                //   LoginScreen()

               // DateTimePicker()
                DateTimeSample2 {
                    Log.d("MyLog", "listeners onclose selection")
                }
                // val booleanValue by booleanFlow.collectAsState(initial = false) WILL BE USED FOR UI SETTINGS
            }
        }

    }
}
