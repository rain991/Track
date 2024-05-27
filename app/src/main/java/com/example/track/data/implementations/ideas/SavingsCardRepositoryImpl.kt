package com.example.track.data.implementations.ideas

import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.repository.ideas.SavingsCardRepository
import java.time.LocalDate

class SavingsCardRepositoryImpl(
//    private val ideaDao: IdeaDao,
    private val incomeDao: IncomeDao,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
) : SavingsCardRepository {
    override suspend fun addToSavings(idea: Idea, value: Float, isIncludedInBudget: Boolean) {
            incomeDao.insert(
                IncomeItem(
                    currencyTicker = "sd",
                    categoryId = incomesCategoriesListRepositoryImpl.getOtherCategoryId(),
                    value = value,
                    date = convertLocalDateToDate(
                        LocalDate.now()
                    ), disabled = true,
                    note = ""
                )
            )
        }


    override fun requestPlannedSavings(idea: Idea): Float {
        return idea.goal
    }

    override fun requestIncludedInBudget(idea: Idea): Boolean? {
        return false  //idea.relatedToAllCategories
    }

    override fun requestCompletenessValue(idea: Idea): Float {
        return 0.0f//idea.currentValue!!
    }

    override fun requestCompletenessRate(idea: Idea): Float {
        return 0.0f //idea.goal.div(idea.currentValue!!)
    }

}