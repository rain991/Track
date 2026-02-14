package com.savenko.track.domain.repository.incomes

import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

interface IncomeCoreRepository {
    fun getSumOfIncomesInTimeSpan(startOfSpan: Instant, endOfSpan: Instant): Flow<Float>
    fun getSumOfIncomesInTimeSpanByCategoriesIds(startOfSpan: Instant, endOfSpan: Instant, categoriesIds : List<Int>): Flow<Float>
    fun getCountOfIncomesInSpan(startDate: Instant, endDate: Instant): Flow<Int>
    fun getCountOfIncomesInSpanByCategoriesIds(startDate: Instant, endDate: Instant, categoriesIds: List<Int>): Flow<Int>
    fun getAverageInTimeSpan(startDate: Instant, endDate: Instant) : Flow<Float>
}