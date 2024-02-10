package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ExpensesListRepositoryImpl(private val expensesDao: ExpensesDAO) : ExpensesListRepository {
    private var expensesList = listOf<ExpenseItem>()
    override suspend fun setExpensesList(expensesDAO: ExpensesDAO, context: CoroutineContext) {
        withContext(Dispatchers.IO) {
            expensesDAO.getAll().collect {
                expensesList = it
            }
        }
    }

    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expensesDao.insertItem(currentExpensesItem)
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
            expensesDao.deleteItem(currentExpenseItem)
        }
    }

    override suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext) {
        expensesDao.update(newExpenseItem)
    }

    override fun sortExpensesItemsDateAsc() {
        expensesList = expensesList.sortedBy { it.date }.toMutableList()
    }

    override fun sortExpensesItemsDateDesc() {
        expensesList = expensesList.sortedByDescending { it.date }.toMutableList()
    }
}