package com.example.track.domain.repository.ideas

import java.util.Date

interface IncomePlanCardRepository {
    fun requestPlannedIncome() : Float
    fun requestStartOfPeriod() : Date
    fun requestEndOfPeriod() : Date
    fun requestCompletenceValue() : Float
    fun requestCompletenceRate() : Float
}