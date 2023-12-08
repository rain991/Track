package com.example.expensetracker.data

import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.domain.ExpensesListRepository

object ExpensesListRepositoryImpl : ExpensesListRepository {
    val expensesList = mutableListOf<ExpenseItem>()
    var autoIncrementId = 0  // all autoIncrementId will be canceled ones the DB connected


    override fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        if(currentExpensesItem.id== ExpenseItem.UNDEFINED_ID){
            currentExpensesItem.id = autoIncrementId++ // post increment
        }else{

        }

        expensesList.add(currentExpensesItem)
    }
    override fun getExpensesList(): List<ExpenseItem> {
        return expensesList.toList() // gets a copy of list
    }

    override fun getExpensesItem(expensesItemId: Int): ExpenseItem {
        if (expensesList.find { it.id == expensesItemId } == null){
            TODO()
        }else{
            return expensesList.find { it.id == expensesItemId }!! // WARNING !! call, to be checked afterwards
        }
    }


    override fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesList.remove(currentExpenseItem)
    }

    override fun editExpenseItem(currentExpenseItem: ExpenseItem) {
        val olderExpense = getExpensesItem(currentExpenseItem.id)
        expensesList.remove(olderExpense)
        addExpensesItem(currentExpenseItem)
    }
}