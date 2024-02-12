package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ExpensesListRepositoryImpl(private val expenseItemsDao: ExpenseItemsDAO) : ExpensesListRepository {
    private var expensesList = listOf<ExpenseItem>()
    override suspend fun setExpensesList(expenseItemsDAO: ExpenseItemsDAO, context: CoroutineContext) {
        withContext(Dispatchers.IO) {
            expenseItemsDAO.getAll().collect {
                expensesList = it
            }
        }
    }

    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.insertItem(currentExpensesItem)
        }
    }


    override fun getExpensesList(): List<ExpenseItem> {
        return expensesList
    }

    override fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
        return expensesList.find { it.id == expensesItemId }
    }


    override suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.deleteItem(currentExpenseItem)
        }
    }

    override suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext) {
        expenseItemsDao.update(newExpenseItem)
    }

    override fun sortExpensesItemsDateAsc() {
        expensesList = expensesList.sortedBy { it.date }
    }

    override fun sortExpensesItemsDateDesc() {
        expensesList = expensesList.sortedByDescending { it.date }
    }
}