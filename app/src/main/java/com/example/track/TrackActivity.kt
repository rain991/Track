package com.example.track


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.workers.CurrenciesRatesWorker
import com.example.track.presentation.navigation.Navigation
import com.example.track.presentation.themes.ThemeManager
import com.example.track.presentation.themes.getThemeByName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class TrackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore: DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStore.loginCountFlow.first()
            if (actualLoginCount > 0) dataStore.incrementLoginCount()
        }
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<CurrenciesRatesWorker>(
            1, TimeUnit.DAYS
        ).setConstraints(constraints).setInputData(workDataOf()).build()
        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork("currenciesRateRequest", ExistingPeriodicWorkPolicy.KEEP, workRequest)
        setContent {
            val useSystemTheme = dataStore.useSystemTheme.collectAsState(initial = false)
            val preferableTheme = dataStore.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
            ThemeManager(isUsingDynamicColors = useSystemTheme.value, preferableTheme = getThemeByName(preferableTheme.value)) {
                Navigation(dataStore)
            }
        }
    }
}