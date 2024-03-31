package com.example.track.domain.repository.notes

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
   suspend fun requestCountOfExpensesAnualy(): Int
   suspend fun requestCountOfIncomeAnualy(): Int
   suspend fun requestBiggestExpenseMonthly(): Float?
   suspend fun requestBiggestIncomeMonthly(): Float?
   suspend fun requestBiggestExpenseAnualy(): Float?
   suspend fun requestBiggestIncomeAnualy(): Float?
   suspend fun requestLoginCounts() : Int
   suspend fun requestIdeasCount() : Int
}