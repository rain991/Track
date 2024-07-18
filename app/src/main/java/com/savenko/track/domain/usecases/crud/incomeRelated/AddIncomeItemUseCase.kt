package com.savenko.track.domain.usecases.crud.incomeRelated

import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeItemRepository

class AddIncomeItemUseCase(private val incomeItemRepositoryImpl: IncomeItemRepository) {
    suspend operator fun invoke(incomeItem : IncomeItem) {
        incomeItemRepositoryImpl.addIncomeItem(incomeItem)
    }
}