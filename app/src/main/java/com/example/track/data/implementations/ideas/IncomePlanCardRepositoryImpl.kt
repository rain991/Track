package com.example.track.data.implementations.ideas

import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.domain.models.idea.Idea
import com.example.track.domain.repository.ideas.IncomePlanCardRepository
import java.util.Date

class IncomePlanCardRepositoryImpl(private val incomeDao: IncomeDao)  : IncomePlanCardRepository{
    override fun requestPlannedIncome(idea: Idea): Float {
        return idea.goal.toFloat()
    }

    override fun requestStartOfPeriod(idea: Idea): Date {
        return idea.startDate
    }

    override fun requestEndOfPeriod(idea: Idea): Date? {
        return idea.endDate
    }

    override suspend fun requestCompletenessValue(idea: Idea): Float {
    return incomeDao.getSumOfIncomesInTimeSpan(start = idea.startDate.time, end = idea.endDate!!.time) //  warning !! call
    }

    override suspend fun requestCompletenessRate(idea: Idea): Float {
        return idea.goal.div(incomeDao.getSumOfIncomesInTimeSpan(start = idea.startDate.time, end = idea.endDate!!.time))
    }
    override suspend fun getSumOfIncomesInTimeSpan(startOfSpan: Date, endOfSpan: Date): Float {
        return incomeDao.getSumOfIncomesInTimeSpan(start = startOfSpan.time, end = endOfSpan.time)
    }

}