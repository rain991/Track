package com.savenko.track.shared.domain.usecases.crud.incomeRelated

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.domain.repository.incomes.IncomeItemRepository

class AddIncomeItemUseCase(private val incomeItemRepositoryImpl: IncomeItemRepository) {
    suspend operator fun invoke(incomeItem : IncomeItem) {
        incomeItemRepositoryImpl.addIncomeItem(incomeItem)
    }
}