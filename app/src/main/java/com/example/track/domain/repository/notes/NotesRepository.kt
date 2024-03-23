package com.example.track.domain.repository.notes

import java.util.Date


interface NotesRepository {
    fun requestSumOfExpensesMonthly(): Float
    fun requestSumOfIncomesMonthly(): Float
    fun requestCountOfExpensesInSpan(startDate : Date, endDate: Date): Int
    fun requestCountOfIncomesInSpan(startDate : Date, endDate: Date): Int
    fun requestCountOfExpensesMonthly(): Int
    fun requestCountOfIncomesMonthly(): Int
    fun requestCountOfExpensesWeekly(): Int
    fun requestCountOfIncomesWeekly(): Int
    fun requestCountOfExpensesAnualy(): Int
    fun requestCountOfIncomeAnualy(): Int
    fun requestBiggestExpenseMonthly(): Float
    fun requestBiggestIncomeMonthly(): Float
    fun requestBiggestExpenseAnualy(): Float
    fun requestBiggestIncomeAnualy(): Float
    suspend fun requestLoginCounts() : Int
    fun requestIdeasCount() : Int
}