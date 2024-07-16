package com.savenko.track.domain.usecases.userData.other

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.currency.Currency
import kotlinx.coroutines.flow.first

// ChangePreferableCurrencyUseCase returns true if currency changing operation was success
class ChangePreferableCurrencyUseCase(
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val dataStoreManager: DataStoreManager,
    private val ideaListRepositoryImpl: IdeaListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
) {
    suspend operator fun invoke(
        targetCurrency: Currency,
        currentPreferableCurrency: Currency,
        firstAdditionalCurrency: Currency?,
        secondAdditionalCurrency: Currency?,
        thirdAdditionalCurrency: Currency?,
        fourthAdditionalCurrency: Currency?,
    ): Boolean {
        if (firstAdditionalCurrency != targetCurrency && secondAdditionalCurrency != targetCurrency &&
            thirdAdditionalCurrency != targetCurrency && fourthAdditionalCurrency != targetCurrency
        ) {
            dataStoreManager.setPreference(
                key = DataStoreManager.BUDGET, value =
                currenciesRatesHandler.convertValueToAnyCurrency(
                    dataStoreManager.budgetFlow.first(),
                    currentPreferableCurrency,
                    targetCurrency
                )
            )
            ideaListRepositoryImpl.changePreferableCurrenciesOnIdeas(targetCurrency, currentPreferableCurrency)
            currenciesPreferenceRepositoryImpl.setPreferableCurrency(targetCurrency)
            return true
        } else {
            return false
        }
    }
}