package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.ExpenseCategory

@Dao
interface ExpenseCategoryDao {

    @Insert
    suspend fun insert(category: ExpenseCategory)

    @Update
    suspend fun update(category: ExpenseCategory)

    @Delete
    suspend fun delete(category: ExpenseCategory)

    @Query("SELECT * FROM expense_categories")
    suspend fun getAllCategories(): List<ExpenseCategory>
}