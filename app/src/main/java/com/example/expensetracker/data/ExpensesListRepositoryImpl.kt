package com.example.expensetracker.data

import android.content.Context
import androidx.lifecycle.lifecycleScope
import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.domain.ExpensesListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
object ExpensesListRepositoryImpl : ExpensesListRepository {
    private var expensesList = mutableListOf<ExpenseItem>()
    var autoIncrementId = 0  // all autoIncrementId will be canceled ones the DB connected
    override suspend fun setExpensesList(expensesDAO: ExpensesDAO) {
        coroutineScope { expensesList=expensesDAO.getAll() }
        }


    override fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        if(currentExpensesItem.id== ExpenseItem.UNDEFINED_ID){
            currentExpensesItem.id = autoIncrementId++ // post increment
        }else{

        }
        expensesList.add(currentExpensesItem)
//        val job = CoroutineScope(Dispatchers.IO).launch {
//            expensesDAO.insertItem(currentExpensesItem)
//        }
    }
    override fun getExpensesList(): MutableList<ExpenseItem> {
        return expensesList.toMutableList() // gets a copy of list
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