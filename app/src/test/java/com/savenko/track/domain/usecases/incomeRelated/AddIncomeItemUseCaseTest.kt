package com.savenko.track.domain.usecases.incomeRelated

import com.savenko.track.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class AddIncomeItemUseCaseTest {

    private val incomeItemRepositoryImpl = mock<IncomeItemRepositoryImpl>()

    @Test
    fun `income item is added successfully`() = runBlocking {
        val incomeItem = mock<IncomeItem>()
        val addIncomeItemUseCase = AddIncomeItemUseCase(incomeItemRepositoryImpl)
        addIncomeItemUseCase(incomeItem)
        verify(incomeItemRepositoryImpl).addIncomeItem(incomeItem)
    }
}