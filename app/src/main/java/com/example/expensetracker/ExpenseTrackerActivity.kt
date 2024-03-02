package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.workers.CurrenciesRatesWorker
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ExpenseTrackerActivity : ComponentActivity() {
    private val currencyListRepositoryImpl by inject<CurrencyListRepositoryImpl>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore: DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStore.loginCountFlow.first()
            if (actualLoginCount > 0) dataStore.incrementLoginCount()
        }

        val workRequest = OneTimeWorkRequestBuilder<CurrenciesRatesWorker>().setInputData(workDataOf()).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        setContent {
            ExpenseTrackerTheme {
                Navigation(dataStore)
            }
        }
    }
}