package com.example.expensetracker.data

import com.example.expensetracker.domain.ExpenseItem
import com.example.expensetracker.domain.ExpensesListRepository
import kotlinx.coroutines.coroutineScope
import java.time.LocalDate
import kotlin.random.Random

object ExpensesListRepositoryImpl : ExpensesListRepository {
    private var expensesList = mutableListOf<ExpenseItem>()
    var autoIncrementId = 0  // all autoIncrementId will be canceled ones the DB connected
    override suspend fun setExpensesList(expensesDAO: ExpensesDAO) {
        coroutineScope { expensesList=expensesDAO.getAll() }
        }

    override fun sortExpensesItemsAsc() {
        expensesList = expensesList.sortedBy { it.date }.toMutableList()
    }

    override fun sortExpensesItemsDesc() {
        expensesList = expensesList.sortedByDescending {  it.date }.toMutableList()
    }


    override fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        if(currentExpensesItem.id== ExpenseItem.UNDEFINED_ID){
            currentExpensesItem.id = autoIncrementId++ // post increment
        }else{ }
        expensesList.add(currentExpensesItem)
//        val job = CoroutineScope(Dispatchers.IO).launch {
//            expensesDAO.insertItem(currentExpensesItem)
//        }
    }
    override fun getExpensesList(): MutableList<ExpenseItem> {
        return expensesList // gets a copy of list (changed: now returns original)
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


// All random elements will be deleted
    fun generateRandomDate(): LocalDate {
        val randomDays = Random.nextLong(180)
        return LocalDate.now().minusDays(randomDays)
    }

    fun generateRandomExpenseObject(): ExpenseItem {
        return ExpenseItem(
           id= Random.nextInt(50000)+5000,
            name = "",
            date = generateRandomDate().toString(),
            enabled=false,
            value= (Random.nextInt(5000)+5).toFloat() // 5-5005
        )
    }
}