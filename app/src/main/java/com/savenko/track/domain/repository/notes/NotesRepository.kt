package com.savenko.track.domain.repository.notes

import java.util.Date


interface NotesRepository {
    suspend fun requestSumOfExpensesMonthly(): Float
    suspend fun requestSumOfIncomesMonthly(): Float
    suspend fun requestCountOfExpensesInSpan(startDate : Date, endDate: Date): Int
    suspend fun requestCountOfIncomesInSpan(startDate : Date, endDate: Date): Int
    suspend fun requestCountOfExpensesMonthly(): Int
   suspend fun requestCountOfIncomesMonthly(): Int
   suspend fun requestCountOfExpensesWeekly(): Int
   suspend fun requestCountOfIncomesWeekly(): Int
   suspend fun requestCountOfExpensesAnnually(): Int
   suspend fun requestCountOfIncomeAnnually(): Int
   suspend fun requestBiggestExpenseMonthly(): Float?
   suspend fun requestBiggestIncomeMonthly(): Float?
   suspend fun requestBiggestExpenseAnnually(): Float?
   suspend fun requestBiggestIncomeAnnually(): Float?
   suspend fun requestLoginCounts() : Int
   suspend fun requestIdeasCount() : Int
}