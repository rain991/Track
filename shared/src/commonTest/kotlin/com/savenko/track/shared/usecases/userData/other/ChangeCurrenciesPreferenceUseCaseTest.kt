package com.savenko.track.shared.domain.usecases.userData.other

import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.testdoubles.FakeCurrenciesPreferenceRepository
import com.savenko.track.shared.testdoubles.FakeCurrencyDao
import com.savenko.track.shared.testdoubles.FakeIdeaListRepository
import com.savenko.track.shared.testdoubles.InMemoryPreferencesDataStore
import com.savenko.track.shared.testdoubles.testCurrency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChangeCurrenciesPreferenceUseCaseTest {
    private val currentPreferableCurrency = testCurrency(ticker = "EUR", rate = 1.2)
    private val targetCurrency = testCurrency(ticker = "USD", rate = 1.0)

    private val dataStoreManager = DataStoreManager(InMemoryPreferencesDataStore())
    private val ideaListRepository = FakeIdeaListRepository()
    private val currenciesPreferenceRepository = FakeCurrenciesPreferenceRepository(currentPreferableCurrency)

    private val currenciesRatesHandler = CurrenciesRatesHandler(
        currencyDao = FakeCurrencyDao(),
        currenciesPreferenceRepositoryImpl = currenciesPreferenceRepository
    )

    private val useCase = ChangeCurrenciesPreferenceUseCase(
        currenciesRatesHandler = currenciesRatesHandler,
        dataStoreManager = dataStoreManager,
        ideaListRepositoryImpl = ideaListRepository,
        currenciesPreferenceRepositoryImpl = currenciesPreferenceRepository
    )

    @Test
    fun `use case successfully changes currency when target is not an additional currency`() = runTest {
        dataStoreManager.setPreference(DataStoreManager.BUDGET, 100f)
        val firstAdditionalCurrency = testCurrency(ticker = "UAH", rate = 40.0)

        val result = useCase.invoke(
            targetCurrency = targetCurrency,
            currentPreferableCurrency = currentPreferableCurrency,
            firstAdditionalCurrency = firstAdditionalCurrency,
            secondAdditionalCurrency = null,
            thirdAdditionalCurrency = null,
            fourthAdditionalCurrency = null
        )

        assertTrue(result)
        assertEquals(120f, dataStoreManager.budgetFlow.first())
        assertEquals(targetCurrency, currenciesPreferenceRepository.getPreferableCurrency().first())
        assertEquals(listOf(targetCurrency to currentPreferableCurrency), ideaListRepository.currencyChanges)
        assertEquals(null, currenciesPreferenceRepository.getFirstAdditionalCurrency().first())
    }

    @Test
    fun `use case swaps with additional currency when target is one of the additional currencies`() = runTest {
        dataStoreManager.setPreference(DataStoreManager.BUDGET, 100f)

        val result = useCase.invoke(
            targetCurrency = targetCurrency,
            currentPreferableCurrency = currentPreferableCurrency,
            firstAdditionalCurrency = targetCurrency,
            secondAdditionalCurrency = null,
            thirdAdditionalCurrency = null,
            fourthAdditionalCurrency = null
        )

        assertTrue(result)
        assertEquals(120f, dataStoreManager.budgetFlow.first())
        assertEquals(targetCurrency, currenciesPreferenceRepository.getPreferableCurrency().first())
        assertEquals(currentPreferableCurrency, currenciesPreferenceRepository.getFirstAdditionalCurrency().first())
    }

    @Test
    fun `use case does not update budget or currency when conversion is incorrect`() = runTest {
        dataStoreManager.setPreference(DataStoreManager.BUDGET, 100f)
        val invalidTargetCurrency = testCurrency(ticker = "GBP", rate = null)

        val result = useCase.invoke(
            targetCurrency = invalidTargetCurrency,
            currentPreferableCurrency = currentPreferableCurrency,
            firstAdditionalCurrency = testCurrency(ticker = "UAH", rate = 40.0),
            secondAdditionalCurrency = null,
            thirdAdditionalCurrency = null,
            fourthAdditionalCurrency = null
        )

        assertFalse(result)
        assertEquals(100f, dataStoreManager.budgetFlow.first())
        assertEquals(currentPreferableCurrency, currenciesPreferenceRepository.getPreferableCurrency().first())
        assertTrue(ideaListRepository.currencyChanges.isEmpty())
    }
}
