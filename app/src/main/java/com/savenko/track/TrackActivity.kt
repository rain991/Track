package com.savenko.track


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.savenko.track.data.other.constants.CURRENCIES_RATES_REQUEST_PERIOD
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.other.workers.CurrenciesRatesWorker
import com.savenko.track.presentation.navigation.Navigation
import com.savenko.track.presentation.states.screenRelated.SplashScreen
import com.savenko.track.presentation.themes.ThemeManager
import com.savenko.track.presentation.themes.getThemeByName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class TrackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager: DataStoreManager by inject()
        CoroutineScope(Dispatchers.IO).launch {
            val actualLoginCount = dataStoreManager.loginCountFlow.first()
            if (actualLoginCount > 0) dataStoreManager.incrementLoginCount()
        }
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = PeriodicWorkRequestBuilder<CurrenciesRatesWorker>(
            repeatInterval = CURRENCIES_RATES_REQUEST_PERIOD, repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(constraints).setInputData(workDataOf()).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "currenciesRateRequest",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
        setContent {
            val useSystemTheme = dataStoreManager.useSystemTheme.collectAsState(initial = false)
            val preferableTheme =
                dataStoreManager.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
            var isSplashScreenVisible by remember { mutableStateOf(true) }
            LaunchedEffect(key1 = preferableTheme.value) {
                isSplashScreenVisible = false
            }
            if (isSplashScreenVisible) {
                SplashScreen()
            } else {
                ThemeManager(
                    isUsingDynamicColors = useSystemTheme.value,
                    preferableTheme = getThemeByName(preferableTheme.value)
                ) {
                    Navigation(dataStoreManager)
                }
            }
        }
    }
}