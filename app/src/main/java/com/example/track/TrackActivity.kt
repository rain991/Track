package com.example.track


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.track.data.DataStoreManager
import com.example.track.presentation.navigation.Navigation
import com.example.track.ui.theme.TrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TrackActivity : ComponentActivity() {
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
            TrackerTheme {
                Navigation(dataStore)
            }
        }
    }
}