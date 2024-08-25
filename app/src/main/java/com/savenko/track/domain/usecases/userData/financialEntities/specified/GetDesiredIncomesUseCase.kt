package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow

class GetDesiredIncomesUseCase(private val incomeListRepositoryImpl: IncomeListRepository) {
    suspend operator fun invoke(startOfSpan: Long, endOfSpan: Long): Flow<List<IncomeItem>> {
        return incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(
            startOfSpan,
            endOfSpan
        )
    }
}