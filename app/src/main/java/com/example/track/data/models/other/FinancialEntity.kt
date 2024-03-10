package com.example.track.data.models.other

import java.util.Date

abstract class FinancialEntity {
    abstract val id: Int
    abstract val currencyTicker: String
    abstract val value: Float
    abstract val note: String
    abstract val date: Date
    abstract val enabled: Boolean
    abstract val categoryId: Int
}