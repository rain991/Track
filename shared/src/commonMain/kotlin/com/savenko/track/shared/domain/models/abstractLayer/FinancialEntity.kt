package com.savenko.track.shared.domain.models.abstractLayer

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


abstract class FinancialEntity {
    abstract val id: Int
    abstract val currencyTicker: String
    abstract val value: Float
    abstract val note: String
    abstract val date: Long
    abstract val disabled: Boolean
    abstract val categoryId: Int
}

sealed class FinancialEntities(val nameId: StringResource) {
    class ExpenseFinancialEntity : FinancialEntities(nameId = Res.string.expense)
    class IncomeFinancialEntity : FinancialEntities(nameId =  Res.string.income)
    class Both : FinancialEntities(nameId = Res.string.both)
}
