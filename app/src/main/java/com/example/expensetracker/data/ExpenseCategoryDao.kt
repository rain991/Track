package com.example.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpenseCategoryDao {

    @Insert
    suspend fun insert(category: ExpenseCategoryEntity)

    @Update
    suspend fun update(category: ExpenseCategoryEntity)

    @Delete
    suspend fun delete(category: ExpenseCategoryEntity)

    @Query("SELECT * FROM expense_categories")
    suspend fun getAllCategories(): List<ExpenseCategoryEntity>
}