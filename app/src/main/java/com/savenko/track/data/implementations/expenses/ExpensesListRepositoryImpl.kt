package com.savenko.track.data.implementations.expenses

import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class ExpensesListRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO
) : ExpensesListRepository {
    override fun getExpensesList(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAll()
    }

    override fun getExpensesListInTimeSpanDateDesc(startOfSpan: Date, endOfSpan: Date): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getExpensesInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time)
    }

    override fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem> {
        return expenseItemsDao.getExpensesByIds(listOfIds)
    }

    override fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateAsc()
    }

    override fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateDesc()
    }

    override fun getExpensesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: ExpenseCategory): List<ExpenseItem> {
        return expenseItemsDao.findExpensesInTimeSpan(start = startOfSpan.time, end = endOfSpan.time, categoryId = category.categoryId)
    }
}