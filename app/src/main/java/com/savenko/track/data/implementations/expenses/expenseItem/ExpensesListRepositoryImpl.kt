package com.savenko.track.data.implementations.expenses.expenseItem

import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow

class ExpensesListRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO
) : ExpensesListRepository {
    override fun getExpensesList(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAll()
    }

    override fun getExpensesListInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getExpensesInTimeSpanDateDesc(startOfSpan, endOfSpan)
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

    override fun getExpensesByCategoryInTimeSpan(startOfSpan: Long, endOfSpan: Long, category: ExpenseCategory): List<ExpenseItem> {
        return expenseItemsDao.findExpensesInTimeSpan(start = startOfSpan, end = endOfSpan, categoryId = category.categoryId)
    }
}