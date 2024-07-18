package com.savenko.track

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.savenko.track.data.core.WorkManagerHelper
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.presentation.navigation.Navigation
import com.savenko.track.presentation.themes.ThemeManager
import com.savenko.track.presentation.themes.getThemeByName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TrackActivity : ComponentActivity() {
    private val dataStoreManager: DataStoreManager by inject()
    private val workManagerHelper: WorkManagerHelper by inject()
    private var actualLoginCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        handleLoginCounter()
        workManagerHelper.setupWorkManager()
        workManagerHelper.checkAndUpdateCurrencyRates()
        setContent {
            val useSystemTheme = dataStoreManager.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
            val preferableTheme =
                dataStoreManager.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
            ThemeManager(
                isUsingDynamicColors = useSystemTheme.value,
                preferableTheme = getThemeByName(preferableTheme.value)
            ) {
                Navigation(actualLoginCount)
            }
        }
    }

    private fun handleLoginCounter() {
        CoroutineScope(Dispatchers.IO).launch {
            actualLoginCount = dataStoreManager.loginCountFlow.first()
            if (actualLoginCount > 0) dataStoreManager.incrementLoginCount()
        }
    }
}
