package com.example.track.data.database.expensesRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(category: ExpenseCategory)

    @Update
    suspend fun update(category: ExpenseCategory)

    @Delete
    suspend fun delete(category: ExpenseCategory)

    @Query("SELECT * FROM expense_categories")
    fun getAllCategories(): Flow<List<ExpenseCategory>>

    @Query("SELECT * FROM expense_categories WHERE categoryId = :id")
    fun getCategoryById(id : Int) : ExpenseCategory

    @Query("DELETE FROM expense_categories")
    suspend fun deleteAllData()
}