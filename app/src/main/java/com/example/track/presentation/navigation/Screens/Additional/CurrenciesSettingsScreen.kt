package com.example.track.presentation.navigation.Screens.Additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.track.presentation.components.settingsScreen.components.CurrenciesSettings
import com.example.track.presentation.navigation.Screen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun CurrenciesSettingsScreen(navController: NavHostController) {
    val settingsData = koinInject<DataStoreManager>()
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val preferableCurrencyState = settingsViewModel.preferableCurrencyStateFlow.collectAsState(initial = CURRENCY_DEFAULT)
    val firstAdditionalCurrencyState = settingsViewModel.firstAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val secondAdditionalCurrencyState = settingsViewModel.secondAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val thirdAdditionalCurrencyState = settingsViewModel.thirdAdditionalCurrencyStateFlow.collectAsState(initial = null)
    val fourthAdditionalCurrencyState =
        settingsViewModel.fourthAdditionalCurrencyStateFlow.collectAsState(
            initial = null
        )
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier= Modifier.fillMaxWidth().padding(start = 4.dp), verticalAlignment = Alignment.CenterVertically){
                Button(onClick = { navController.navigate(Screen.MainScreen.route) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back to settings screen")
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Currencies", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.weight(1.2f))
            }
        },bottomBar = { }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {
            Spacer(modifier = Modifier.height(10.dp))
            CurrenciesSettings(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                preferableCurrency = preferableCurrencyState.value!!,
                firstAdditionalCurrency = firstAdditionalCurrencyState.value,
                secondAdditionalCurrency = secondAdditionalCurrencyState.value,
                thirdAdditionalCurrency = thirdAdditionalCurrencyState.value,
                fourthAdditionalCurrency = fourthAdditionalCurrencyState.value
            )
        }
    }
}