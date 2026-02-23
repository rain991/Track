package com.savenko.track.shared.domain.usecases.userData.other

import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.models.currency.CurrencyTypes
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaListRepository
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
                com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.currentPreferableCurrency,
                com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.targetCurrency
            )
        )
            .thenReturn(convertedValue)

        whenever(
            ideaListRepositoryImpl.changePreferableCurrenciesOnIdeas(
                com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.targetCurrency,
                com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.currentPreferableCurrency
            )
        ).then { }

        whenever(currenciesPreferenceRepositoryImpl.setPreferableCurrency(com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.targetCurrency)).then { }

        val result = changeCurrenciesPreferenceUseCase.invoke(
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.targetCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.currentPreferableCurrency,
            firstAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.secondAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.thirdAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.fourthAdditionalCurrency
        )

        assertTrue(result)
    }

    @Test
    fun `use case does not change currency when target is one of the additional currencies`() = runTest {
        val firstAdditionalCurrency = Currency("USD", "US", type = CurrencyTypes.FIAT, 0.0)
        val result = changeCurrenciesPreferenceUseCase.invoke(
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.targetCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.currentPreferableCurrency,
            firstAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.secondAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.thirdAdditionalCurrency,
            com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCaseTest.Companion.fourthAdditionalCurrency
        )

        assertFalse(result)
    }
}
