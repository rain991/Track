package com.savenko.track.domain.models.abstractLayer

import com.savenko.track.R
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

sealed class FinancialEntities(val nameId: Int) {
    class ExpenseFinancialEntity : FinancialEntities(nameId = R.string.expense)
    class IncomeFinancialEntity : FinancialEntities(nameId =  R.string.income)
    class Both : FinancialEntities(nameId = R.string.both)
}