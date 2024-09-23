package com.savenko.track

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.savenko.track.data.core.WorkManagerHelper
import com.savenko.track.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.data.other.constants.UNINITIALIZED_LOGIN_COUNT_STATE
import com.savenko.track.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.presentation.navigation.Navigation
import com.savenko.track.presentation.themes.ThemeManager
import com.savenko.track.presentation.themes.getThemeByName
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * **Track** is build as single activity no-fragment app with 100% Kotlin codebase
 *
 * Track uses Jetpack Compose for UI and Jetpack Navigation for navigation
 *
 * To handle screen sizes correctly **Track** in some cases creates child composable for specific screens, e.g :
 * MainTrackScreen shows MainTrackScreenCompactComponent and MainTrackScreenExpandedComponent depending on screen width
 */
class TrackActivity : ComponentActivity() {
    private val dataStoreManager: DataStoreManager by inject()
    private val workManagerHelper: WorkManagerHelper by inject()
    private val trackScreenManagerViewModel by viewModel<TrackScreenManagerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        workManagerHelper.setupPeriodicRequest()
        workManagerHelper.checkAndUpdateCurrencyRates()
        setContent {
            val loginCountState =
                trackScreenManagerViewModel.loginCountValue.collectAsState(initial = UNINITIALIZED_LOGIN_COUNT_STATE)
            splashScreen.setKeepOnScreenCondition {
                loginCountState.value == UNINITIALIZED_LOGIN_COUNT_STATE
            }
            val useSystemTheme = dataStoreManager.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
            val preferableTheme =
                dataStoreManager.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)
            if (loginCountState.value != UNINITIALIZED_LOGIN_COUNT_STATE) {
                ThemeManager(
                    isUsingDynamicColors = useSystemTheme.value,
                    preferableTheme = getThemeByName(preferableTheme.value)
                ) {
                    Navigation(loginCountState.value)
                }
            }
        }
    }
}