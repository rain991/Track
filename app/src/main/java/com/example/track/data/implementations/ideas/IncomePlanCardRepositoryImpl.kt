package com.example.track.data.implementations.ideas

import com.example.track.data.models.idea.Idea
import com.example.track.domain.repository.ideas.IncomePlanCardRepository
import java.util.Date

class IncomePlanCardRepositoryImpl  : IncomePlanCardRepository{
    override fun requestPlannedIncome(idea: Idea): Float {
        TODO("Not yet implemented")
    }

    override fun requestStartOfPeriod(idea: Idea): Date {
        TODO("Not yet implemented")
    }

    override fun requestEndOfPeriod(idea: Idea): Date {
        TODO("Not yet implemented")
    }

    override fun requestCompletenceValue(idea: Idea): Float {
        TODO("Not yet implemented")
    }

    override fun requestCompletenceRate(idea: Idea): Float {
        TODO("Not yet implemented")
    }


}