package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow

class GetUserIncomesUseCase(private val incomeListRepositoryImpl: IncomeListRepository) {
    fun getUserIncomesDateDesc(): Flow<List<IncomeItem>> {
        return incomeListRepositoryImpl.getSortedIncomesListDateDesc()
    }
}