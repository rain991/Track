package com.example.track.domain.repository.ideas

import com.example.track.data.models.idea.Idea
import java.util.Date

interface IncomePlanCardRepository {
    fun requestPlannedIncome(idea: Idea) : Float
    fun requestStartOfPeriod(idea: Idea) : Date
    fun requestEndOfPeriod(idea: Idea) : Date?
    suspend fun requestCompletenessValue(idea: Idea) : Float
    suspend fun requestCompletenessRate(idea: Idea) : Float
    suspend fun getSumOfIncomesInTimeSpan(startOfSpan : Date, endOfSpan : Date) : Float
}