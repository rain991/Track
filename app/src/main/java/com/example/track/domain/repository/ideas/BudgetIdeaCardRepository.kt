package com.example.track.domain.repository.ideas

interface BudgetIdeaCardRepository {
fun requestMonthBudget()
fun requestWeekBudget()
fun requestCurrentMonthExpenses()
fun requestBudgetExpectancy() : Float
}