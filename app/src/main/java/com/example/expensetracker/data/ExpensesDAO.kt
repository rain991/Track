package com.example.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(expenseItem : ExpenseItem)
    @Delete
    suspend fun deleteItem(expenseItem : ExpenseItem)
//   @Query("SELECT * FROM Expenses")
//   suspend fun getAllItems()  : Flow<List<ExpenseItem>>
@Query("SELECT * FROM Expenses")
suspend fun getAll(): MutableList<ExpenseItem>
}