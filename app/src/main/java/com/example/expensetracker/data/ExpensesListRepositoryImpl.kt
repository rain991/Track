package com.example.expensetracker.data

import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.domain.ExpensesListRepository

object ExpensesListRepositoryImpl : ExpensesListRepository {
    private val expensesList = mutableListOf<ExpenseItem>()
    override fun getExpensesList(): List<ExpenseItem> {
        return expensesList.toList() // gets a copy of list
    }

    override fun getExpensesItem(expensesItemId: Int): ExpenseItem {
        if (expensesList.find { it.id == expensesItemId } == null){
            TODO()
        }else{
            return expensesList.find { it.id == expensesItemId }!! // Warning !! call, to be checked afterwards
        }
    }

    override fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        expensesList.add(currentExpensesItem)
    }

    override fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesList.remove(currentExpenseItem)
    }

    override fun editExpenseItem(currentExpenseItem: ExpenseItem) {
        TODO("Not yet implemented")
    }
}