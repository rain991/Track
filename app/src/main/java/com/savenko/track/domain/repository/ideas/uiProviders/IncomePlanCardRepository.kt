package com.savenko.track.domain.repository.ideas.uiProviders

import com.savenko.track.domain.models.abstractLayer.Idea
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface IncomePlanCardRepository {
    fun requestPlannedIncome(idea: Idea) : Float
    fun requestStartOfPeriod(idea: Idea) : Date
    fun requestEndOfPeriod(idea: Idea) : Date?
    suspend fun requestCompletenessValue(idea: Idea) : Flow<Float>
    suspend fun requestCompletenessRate(idea: Idea) :  Flow<Float>
    suspend fun getSumOfIncomesInTimeSpan(startOfSpan : Date, endOfSpan : Date) :  Flow<Float>
}