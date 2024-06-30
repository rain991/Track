package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.data.implementations.incomes.incomeItem.IncomeListRepositoryImpl
import com.savenko.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow

class GetUserIncomesUseCase(private val incomeListRepositoryImpl: IncomeListRepositoryImpl) {
    operator fun invoke(): Flow<List<IncomeItem>> {
        return incomeListRepositoryImpl.getSortedIncomesListDateDesc()
    }
}