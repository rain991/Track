package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpensesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE) // previously REPLACE
    suspend fun insertItem(expenseItem: ExpenseItem)

    @Update
    suspend fun update(expenseItem: ExpenseItem)

    @Delete
    suspend fun deleteItem(expenseItem: ExpenseItem)

    @Query("SELECT * FROM Expenses")
    fun getAll(): Flow<List<ExpenseItem>>
}

//    @Query("SELECT * FROM Expenses")
//    suspend fun getAllItems(): Flow<List<ExpenseItem>>