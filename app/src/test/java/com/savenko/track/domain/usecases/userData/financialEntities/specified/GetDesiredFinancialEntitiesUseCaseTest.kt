package com.savenko.track.domain.usecases.userData.financialEntities.specified


import android.util.Range
import com.savenko.track.appModule
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import java.util.Date

@RunWith(RobolectricTestRunner::class)
class GetDesiredFinancialEntitiesUseCaseTest : KoinTest {
    private lateinit var getDesiredFinancialEntitiesUseCase: GetDesiredFinancialEntitiesUseCase
     val incomeListRepositoryImpl by inject<IncomeListRepository>()
     val expensesListRepositoryImpl by inject<ExpensesListRepository>()


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(level = Level.DEBUG)
        modules(appModule)
    }

    @Before
    fun setup() {
        getDesiredFinancialEntitiesUseCase = GetDesiredFinancialEntitiesUseCase(
            incomeListRepositoryImpl = incomeListRepositoryImpl,
            expensesListRepositoryImpl = expensesListRepositoryImpl
        )
    }

    @Test
    fun `returns both income and expense items within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)
        val datesRange = Range(startOfSpan, endOfSpan)

        val expenseItems = listOf(
            ExpenseItem(currencyTicker = "USD", value = 100f, note = "", categoryId = 1, date = Date(5000))
        )
        val incomeItems = listOf(
            IncomeItem(currencyTicker = "UAH", value = 200f, note = "", categoryId = 2, date = Date(6000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(expenseItems) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(incomeItems) })

        val result: List<FinancialEntity> = getDesiredFinancialEntitiesUseCase(datesRange).first()

        assert(result.containsAll(expenseItems))
        assert(result.containsAll(incomeItems))
    }

    @Test
    fun `returns empty list when no income or expense items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)
        val datesRange = Range(startOfSpan, endOfSpan)

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(emptyList()) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(emptyList()) })

        val result: List<FinancialEntity> = getDesiredFinancialEntitiesUseCase(datesRange).first()

        assert(result.isEmpty())
    }

    @Test
    fun `returns only expense items when no income items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)
        val datesRange = Range(startOfSpan, endOfSpan)

        val expenseItems = listOf(
            ExpenseItem(currencyTicker = "USD", value = 100f, note = "", categoryId = 1, date = Date(5000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(expenseItems) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(emptyList()) })

        val result = getDesiredFinancialEntitiesUseCase(datesRange).first()

        assert(result.containsAll(expenseItems))
        assert(result.none { it is IncomeItem })
    }

    @Test
    fun `returns only income items when no expense items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)
        val datesRange = Range(startOfSpan, endOfSpan)

        val incomeItems = listOf(
            IncomeItem(currencyTicker = "USD", value = 200f, note = "", categoryId = 2, date = Date(6000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(emptyList()) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, endOfSpan))
            .thenReturn(flow { emit(incomeItems) })

        val result: List<FinancialEntity> = getDesiredFinancialEntitiesUseCase(datesRange).first()

        assert(result.containsAll(incomeItems))
        assert(result.none { it is ExpenseItem })
    }
}
