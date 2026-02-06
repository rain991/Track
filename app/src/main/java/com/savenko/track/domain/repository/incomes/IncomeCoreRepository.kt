package com.savenko.track.domain.repository.incomes

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IncomeCoreRepository {
    fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Flow<Float>
    fun getSumOfIncomesInTimeSpanByCategoriesIds(startOfSpan: Date, endOfSpan: Date, categoriesIds : List<Int>): Flow<Float>
    fun getCountOfIncomesInSpan(startDate: Date, endDate: Date): Flow<Int>
    fun getCountOfIncomesInSpanByCategoriesIds(startDate: Date, endDate: Date, categoriesIds: List<Int>): Flow<Int>
    fun getAverageInTimeSpan(startDate: Date, endDate: Date) : Flow<Float>
}