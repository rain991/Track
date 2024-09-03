package com.savenko.track.domain.usecases.userData.other

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.CurrencyTypes
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ChangeCurrenciesPreferenceUseCaseTest {

    private lateinit var changeCurrenciesPreferenceUseCase: ChangeCurrenciesPreferenceUseCase
    private val currenciesRatesHandler: CurrenciesRatesHandler = mock()
    private val dataStoreManager: DataStoreManager = mock()
    private val ideaListRepositoryImpl: IdeaListRepository = mock()
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository = mock()


    companion object {
        val targetCurrency = Currency("USD", "US", type = CurrencyTypes.FIAT, 0.0)
        val currentPreferableCurrency = Currency("EUR", "Euro", type = CurrencyTypes.FIAT, 0.0)
        val secondAdditionalCurrency: Currency? = null
        val thirdAdditionalCurrency: Currency? = null
        val fourthAdditionalCurrency: Currency? = null
    }

    @Before
    fun setUp() {
        changeCurrenciesPreferenceUseCase = ChangeCurrenciesPreferenceUseCase(
            currenciesRatesHandler,
            dataStoreManager,
            ideaListRepositoryImpl,
            currenciesPreferenceRepositoryImpl
        )
    }

    @After
    fun tearDown() {
        Mockito.reset(
            currenciesRatesHandler,
            dataStoreManager,
            ideaListRepositoryImpl,
            currenciesPreferenceRepositoryImpl
        )
    }

    @Test
    fun `use case successfully changes currency when target is not an additional currency`() = runTest {
       val firstAdditionalCurrency = Currency("UAH", "Hr", type = CurrencyTypes.FIAT, 0.0)
        val budgetValue = 100.0f
        val convertedValue = 120.0f

        whenever(dataStoreManager.budgetFlow).thenReturn(flow {
            emit(budgetValue)
        })
        whenever(
            currenciesRatesHandler.convertValueToAnyCurrency(
                budgetValue,
                currentPreferableCurrency,
                targetCurrency
            )
        )
            .thenReturn(convertedValue)

        whenever(
            ideaListRepositoryImpl.changePreferableCurrenciesOnIdeas(
                targetCurrency,
                currentPreferableCurrency
            )
        ).then { }

        whenever(currenciesPreferenceRepositoryImpl.setPreferableCurrency(targetCurrency)).then { }

        val result = changeCurrenciesPreferenceUseCase.invoke(
            targetCurrency,
            currentPreferableCurrency,
            firstAdditionalCurrency,
            secondAdditionalCurrency,
            thirdAdditionalCurrency,
            fourthAdditionalCurrency
        )

        assertTrue(result)
    }

    @Test
    fun `use case does not change currency when target is one of the additional currencies`() = runTest {
        val firstAdditionalCurrency = Currency("USD", "US", type = CurrencyTypes.FIAT, 0.0)
        val result = changeCurrenciesPreferenceUseCase.invoke(
            targetCurrency,
            currentPreferableCurrency,
            firstAdditionalCurrency,
            secondAdditionalCurrency,
            thirdAdditionalCurrency,
            fourthAdditionalCurrency
        )

        assertFalse(result)
    }
}
