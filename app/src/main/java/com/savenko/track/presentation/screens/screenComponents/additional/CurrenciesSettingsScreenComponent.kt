package com.savenko.track.presentation.screens.screenComponents.additional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.currenciesSettings.CurrencyListComponent
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.currenciesSettings.SelectedCurrenciesComponent
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.SelectedCurrenciesSettingsState

/**
 *  Contains [SelectedCurrenciesComponent] and [CurrencyListComponent]
 */
@Composable
fun CurrenciesSettingsScreenComponent(
    paddingValues: PaddingValues,
    filteredLazyListCurrencies: List<Currency>,
    state: SelectedCurrenciesSettingsState,
    onAction: (CurrenciesSettingsScreenEvent) -> Unit
) {
    val currenciesPreferenceUI = state.currenciesPreferenceUI
    val additionalCurrenciesPreferenceList = listOf(
        currenciesPreferenceUI.firstAdditionalCurrency,
        currenciesPreferenceUI.secondAdditionalCurrency,
        currenciesPreferenceUI.thirdAdditionalCurrency,
        currenciesPreferenceUI.fourthAdditionalCurrency
    )
    Column(modifier = Modifier.fillMaxSize()) {
        SelectedCurrenciesComponent(
            paddingValues = paddingValues,
            state = state,
            onAction = onAction,
            currenciesPreferenceUI = currenciesPreferenceUI,
            additionalCurrenciesPreferenceList = additionalCurrenciesPreferenceList
        )
        Spacer(modifier = Modifier.height(8.dp))
        CurrencyListComponent(filteredCurrenciesList = filteredLazyListCurrencies, onAction = onAction) {
            onAction(CurrenciesSettingsScreenEvent.SetFilteringText(it))
        }
    }
}