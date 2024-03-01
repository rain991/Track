package com.example.expensetracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.presentation.navigation.Navigation
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class ExpenseTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore: DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStore.loginCountFlow.first()
            if (actualLoginCount > 0) dataStore.incrementLoginCount()
        }
        setContent {
            ExpenseTrackerTheme {
                Navigation(dataStore)
            }
        }
    }
}
//CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = RetrofitClient.api.getLatestRates(API_KEY, "EUR, ETH, PLN")
//                println("Date: ${response.date}")
//                println("Base: ${response.base}")
//                response.rates.forEach { (currency, rate) ->
//                    Log.d("MyLog", "currency : $currency, rate: $rate")
//                }
//            } catch (e: Exception) {
//                Log.d("MyLog", "Error: ${e.message}")
//            }
//        }