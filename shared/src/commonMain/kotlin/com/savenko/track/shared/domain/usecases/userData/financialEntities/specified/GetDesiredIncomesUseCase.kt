package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.Flow

class GetDesiredIncomesUseCase(private val incomeListRepositoryImpl: IncomeListRepository) {
    operator fun invoke(startOfSpan: Long, endOfSpan: Long): Flow<List<IncomeItem>> {
        return incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(
            startOfSpan,
            endOfSpan
        )
    }
}