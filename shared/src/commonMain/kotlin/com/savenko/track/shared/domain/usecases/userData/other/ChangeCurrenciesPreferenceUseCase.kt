package com.savenko.track.shared.domain.usecases.userData.other

import co.touchlab.kermit.Logger
import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.first

class ChangeCurrenciesPreferenceUseCase(
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val dataStoreManager: DataStoreManager,
    private val ideaListRepositoryImpl: IdeaListRepository,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
) {
    /**
     * ChangePreferableCurrencyUseCase returns true if currency changing operation was success
     */
    suspend operator fun invoke(
        targetCurrency: Currency,
        currentPreferableCurrency: Currency,
        firstAdditionalCurrency: Currency?,
        secondAdditionalCurrency: Currency?,
        thirdAdditionalCurrency: Currency?,
        fourthAdditionalCurrency: Currency?,
    ): Boolean {
        if (currentPreferableCurrency.ticker == targetCurrency.ticker) {
            return true
        }

        val previousBudget = dataStoreManager.budgetFlow.first()
        val convertedBudget = currenciesRatesHandler.convertValueToAnyCurrency(
            previousBudget,
            currentPreferableCurrency,
            targetCurrency
        )
        Logger.d("ChangeCurrenciesPreferenceUseCase") {
            "Converted budget when changing preferable currency : $convertedBudget, previousBudget : $previousBudget, $currentPreferableCurrency, for target currency $targetCurrency"
        }
        if (convertedBudget == INCORRECT_CONVERSION_RESULT) {
            return false
        }

        dataStoreManager.setPreference(
            key = DataStoreManager.BUDGET,
            value = convertedBudget
        )
        ideaListRepositoryImpl.changePreferableCurrenciesOnIdeas(
            targetCurrency,
            currentPreferableCurrency
        )
        currenciesPreferenceRepositoryImpl.setPreferableCurrency(targetCurrency)

        when (targetCurrency.ticker) {
            firstAdditionalCurrency?.ticker -> currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(
                currentPreferableCurrency
            )

            secondAdditionalCurrency?.ticker -> currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(
                currentPreferableCurrency
            )

            thirdAdditionalCurrency?.ticker -> currenciesPreferenceRepositoryImpl.setThirdAdditionalCurrency(
                currentPreferableCurrency
            )

            fourthAdditionalCurrency?.ticker -> currenciesPreferenceRepositoryImpl.setFourthAdditionalCurrency(
                currentPreferableCurrency
            )
        }

        return true
    }
}
