package com.savenko.track.data.implementations.expenses

import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpenseItemRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ExpenseItemRepositoryImpl(private val expenseItemsDao: ExpenseItemsDAO) : ExpenseItemRepository{
    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.insertItem(currentExpensesItem)
        }
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
}