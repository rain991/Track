package com.example.track.domain.usecases.incomeRelated

import com.example.track.data.implementations.incomes.IncomeItemRepositoryImpl
import com.example.track.domain.models.incomes.IncomeItem

class AddIncomeItemUseCase(private val incomeItemRepositoryImpl: IncomeItemRepositoryImpl) {
    suspend operator fun invoke(incomeItem : IncomeItem) {
        incomeItemRepositoryImpl.addIncomeItem(incomeItem)
    }
}