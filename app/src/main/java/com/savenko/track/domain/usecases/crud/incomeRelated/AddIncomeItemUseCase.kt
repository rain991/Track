package com.savenko.track.domain.usecases.crud.incomeRelated

import com.savenko.track.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.domain.models.incomes.IncomeItem

class AddIncomeItemUseCase(private val incomeItemRepositoryImpl: IncomeItemRepositoryImpl) {
    suspend operator fun invoke(incomeItem : IncomeItem) {
        incomeItemRepositoryImpl.addIncomeItem(incomeItem)
    }
}