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
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.presentation.navigation.Navigation
import com.savenko.track.presentation.themes.ThemeManager
import com.savenko.track.presentation.themes.getThemeByName
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.koinViewModel

class TrackActivity : ComponentActivity() {
    private val dataStoreManager: DataStoreManager by inject()
    private val workManagerHelper: WorkManagerHelper by inject()


    //  private var actualLoginCount by remember { mutableIntStateOf(0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // handleLoginCounter()
        workManagerHelper.setupWorkManager()
        workManagerHelper.checkAndUpdateCurrencyRates()
        setContent {
            val trackScreenManagerViewModel: TrackScreenManagerViewModel = koinViewModel<TrackScreenManagerViewModel>()
            val loginCount = trackScreenManagerViewModel.loginCountValue.collectAsState(initial = 0)
            val useSystemTheme = dataStoreManager.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
            val preferableTheme =
                dataStoreManager.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
            ThemeManager(
                isUsingDynamicColors = useSystemTheme.value,
                preferableTheme = getThemeByName(preferableTheme.value)
            ) {
                Navigation(loginCount.value)
            }
        }
    }

//    private fun handleLoginCounter() {
//        var blocker = false
//        CoroutineScope(Dispatchers.IO).launch {
//            dataStoreManager.loginCountFlow.collect{
//                actualLoginCount = it
//                if (it > 0 && !blocker) {
//                    dataStoreManager.incrementLoginCount()
//                    blocker = true
//                }
//            }
//        }
//    }
}
