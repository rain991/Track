package com.example.track.data.implementations.expenses

import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.CoroutineContext

class ExpensesListRepositoryImpl(private val expenseItemsDao: ExpenseItemsDAO) : ExpensesListRepository {
    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.insertItem(currentExpensesItem)
        }
    }

    override fun getExpensesList(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAll()
    }

    override fun getExpenseItem(expensesItemId: Int): ExpenseItem? {
        return expenseItemsDao.findExpenseById(expensesItemId)
    }

    override suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.deleteItem(currentExpenseItem)
        }
    }

    override suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext) {
        expenseItemsDao.update(newExpenseItem)
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