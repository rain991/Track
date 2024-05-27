package com.example.track.data.implementations.ideas

import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.repository.ideas.ExpenseLimitsCardRepository

class ExpenseLimitsCardRepositoryImpl(
    private val expensesCategoriesListRepositoryImpl: ExpensesCategoriesListRepositoryImpl
) : ExpenseLimitsCardRepository {
    override fun requestPlannedLimit(idea: Idea): Float {
        return idea.goal.toFloat()
    }

    override fun requestRelatedToAllCategories(idea: Idea): Boolean? {
        return false // idea.relatedToAllCategories
    }

    override fun requestListOfChosenCategories(idea: Idea): List<ExpenseCategory> {
        return if(requestRelatedToAllCategories(idea) == false) {
            val list = listOf<Int>(/*idea.firstRelatedCategoryId, idea.secondRelatedCategoryId, idea.thirdRelatedCategoryId*/)
            expensesCategoriesListRepositoryImpl.getCategoriesByIds(list.filterNotNull())
        }else{
            emptyList()
        }
    }

    override fun requestAlreadySpentValue(idea: Idea): Float {
//        return if(idea.relatedToAllCategories == true){
//            expensesListRepositoryImpl.getCurrentMonthSumOfExpenses()
//        }else{
//            val currentChosenCategories = requestListOfChosenCategories(idea)
//            expensesListRepositoryImpl.getCurrentMonthSumOfExpensesForCategories(currentChosenCategories)
//        }
        return 0f
    }
}