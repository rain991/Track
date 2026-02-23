package com.savenko.track.shared.data.implementations.expenses.expenseItem

import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
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

    override suspend fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem> {
        return expenseItemsDao.getExpensesByIds(listOfIds)
    }

    override fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateAsc()
    }

    override fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateDesc()
    }

    override suspend fun getExpensesByCategoryInTimeSpan(startOfSpan: Long, endOfSpan: Long, category: ExpenseCategory): List<ExpenseItem> {
        return expenseItemsDao.findExpensesInTimeSpan(start = startOfSpan, end = endOfSpan, categoryId = category.categoryId)
    }
}
