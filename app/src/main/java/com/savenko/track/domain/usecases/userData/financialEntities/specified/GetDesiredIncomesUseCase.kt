package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeListRepositoryImpl
import com.savenko.track.domain.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetDesiredIncomesUseCase(private val incomeListRepositoryImpl: IncomeListRepositoryImpl) {
    suspend operator fun invoke(timePeriod: Range<Date>): Flow<List<IncomeItem>> {
        return incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(
            timePeriod.lower,
            timePeriod.upper
        )
    }
}