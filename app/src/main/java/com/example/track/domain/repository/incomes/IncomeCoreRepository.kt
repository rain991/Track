package com.example.track.domain.repository.incomes

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IncomeCoreRepository {
    suspend fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Flow<Float>
    suspend fun getSumOfIncomesInTimeSpanByCategoriesIds(startOfSpan: Date, endOfSpan: Date, categoriesIds : List<Int>): Flow<Float>
    suspend fun getCountOfIncomesInSpan(startDate: Date, endDate: Date): Flow<Int>
    suspend fun getCountOfIncomesInSpanByCategoriesIds(startDate: Date, endDate: Date, categoriesIds: List<Int>): Flow<Int>
}