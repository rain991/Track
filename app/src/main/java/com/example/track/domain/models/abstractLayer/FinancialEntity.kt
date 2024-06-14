package com.example.track.domain.models.abstractLayer

import java.util.Date

abstract class FinancialEntity {
    abstract val id: Int
    abstract val currencyTicker: String
    abstract val value: Float
    abstract val note: String
    abstract val date: Date
    abstract val disabled: Boolean
    abstract val categoryId: Int
}

sealed class FinancialEntities(val name: String) {
    class ExpenseFinancialEntity : FinancialEntities(name = "Expense")
    class IncomeFinancialEntity : FinancialEntities(name = "Income")
    class Both : FinancialEntities(name = "Both")
}