package com.savenko.track.domain.usecases.crud.incomeRelated

import com.savenko.track.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class AddIncomeItemUseCaseTest {
    private lateinit var addIncomeItemUseCase: AddIncomeItemUseCase
    private val incomeItemRepositoryImpl = mock<IncomeItemRepositoryImpl>()

    @Before
    fun setup() {
        addIncomeItemUseCase = AddIncomeItemUseCase(incomeItemRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(incomeItemRepositoryImpl)
    }

    @Test
    fun `income item is added successfully`() = runTest {
        val incomeItem = mock<IncomeItem>()
        addIncomeItemUseCase(incomeItem)
        verify(incomeItemRepositoryImpl).addIncomeItem(incomeItem)
    }
}