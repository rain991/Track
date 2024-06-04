package com.example.track.domain.models.notions

sealed class Notion(val name: String) {
    class RequestSumOfExpensesMonthly(val value: Float) : Notion("RequestSumOfExpensesMonthly") {
        val message = "You did $value expenses in current month"
    }

    class RequestSumOfIncomesMonthly(val value: Float) : Notion("RequestSumOfExpensesMonthly") {
        val message = "You did $value incomes in current month"
    }

     class RequestCountOfExpensesEntitiesInTimeSpan(val count: Int) : Notion("RequestCountOfExpensesEntitiesInTimeSpan"){
         val message = "$count expenses in selected time span"
     }
     class RequestCountOfIncomesEntitiesInTimeSpan(val count: Int) : Notion("RequestCountOfIncomesEntitiesInTimeSpan"){
         val message = "$count incomes in selected time span"
     }

     class RequestCountOfExpensesEntitiesMontly(val count: Int, val month : String) : Notion("RequestCountOfExpensesEntitiesMontly"){
         val message = "$month expenses: $count "
     }
     class RequestCountOfIncomesEntitiesMontly(val count: Int, val month : String) : Notion("RequestCountOfIncomesEntitiesMontly"){
         val message = "$month incomes: $count "
     }

     class RequestCountOfExpensesEntitiesWeekly(val count: Int) : Notion("RequestCountOfExpensesEntitiesWeekly"){
         val message = "Expenses this week: $count"
     }
     class RequestCountOfIncomesEntitiesWeekly(val count: Int) : Notion("RequestCountOfIncomesEntitiesWeekly"){
         val message = "Incomes this week: $count"
     }

    class RequestCountOfExpensesEntitiesAnually(val count: Int) : Notion("RequestCountOfExpensesEntitiesAnually"){
        val message = "Expenses this year: $count"
    }
    class RequestCountOfIncomesEntitiesAnually(val count: Int) : Notion("RequestCountOfIncomesEntitiesAnually"){
        val message = "Incomes this year: $count"
    }

    class RequestLoginCount(val count: Int) : Notion("RequestLoginCount"){
        val message = "You have entered app $count times. Hooray!"
    }

    class RequestIdeasCount(val count: Int) : Notion("RequestLoginCount") {
        val message = "You have already created $count ideas"
    }
}
