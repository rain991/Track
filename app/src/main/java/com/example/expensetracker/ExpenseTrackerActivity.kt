package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ExpenseTrackerActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore: DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStore.loginCountFlow.first()
            if (actualLoginCount > 0) dataStore.incrementLoginCount()
        }
//        val workRequest = PeriodicWorkRequestBuilder<CurrenciesRatesWorker>(
//            1, TimeUnit.DAYS
//        ).setInputData(workDataOf()).build()
//        WorkManager.getInstance(applicationContext).enqueue(workRequest)


        setContent {
            ExpenseTrackerTheme {
                Navigation(dataStore)
            }
        }
    }
}