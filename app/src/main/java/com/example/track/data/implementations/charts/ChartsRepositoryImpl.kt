package com.example.track.data.implementations.charts

import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.incomes.IncomeItem
import com.example.track.domain.repository.charts.ChartsRepository

class ChartsRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val expenseItemsDao: ExpenseItemsDAO
) : ChartsRepository {
    override fun requestCurrentMonthExpenses(): List<ExpenseItem> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentMonthIncomes(): List<IncomeItem> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentYearExpenses(): List<ExpenseItem> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentYearIncomes(): List<IncomeItem> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentMonthExpenseCategoriesDistribution(): Map<ExpenseCategory, Int> {
        TODO("Not yet implemented")
    }

    override fun requestCurrentMonthIncomeCategoriesDistribution(): Map<IncomeCategory, Int> {
        TODO("Not yet implemented")
    }
}