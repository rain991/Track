package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
}