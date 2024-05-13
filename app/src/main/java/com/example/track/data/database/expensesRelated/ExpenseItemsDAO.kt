package com.example.track.data.database.expensesRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseItemsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(expenseItem: ExpenseItem)

    @Update
    suspend fun update(expenseItem: ExpenseItem)

    @Delete
    suspend fun deleteItem(expenseItem: ExpenseItem)

    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllWithDateDesc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses ORDER BY date ASC")
    fun getAllWithDateAsc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date DESC")
     fun getExpensesInTimeSpanDateDesc(start: Long, end: Long): Flow<List<ExpenseItem>>
    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date ASC")
     fun getExpensesInTimeSpanDateAsc(start: Long, end: Long): Flow<List<ExpenseItem>>

    @Query("SELECT Count(value) FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getCountOfExpensesInTimeSpan(start: Long, end: Long): Int

    @Query("SELECT SUM(value) FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getSumOfExpensesInTimeSpan(start: Long, end: Long): Float

    @Query("SELECT MAX(value) FROM expenses WHERE date BETWEEN :start AND :end")
    suspend fun getBiggestExpenseInTimeSpan(start: Long, end: Long): Float?

    @Query("SELECT SUM(value) FROM expenses WHERE date BETWEEN :start AND :end")
    fun getSumOfExpensesInTimeSpanInFlow(start: Long, end: Long): Flow<Float>

    @Query("SELECT SUM(value) FROM expenses WHERE categoryId IN (:listOfCategoriesId) AND date BETWEEN :start AND :end")
    suspend fun getSumOfExpensesByCategoriesIdInTimeSpan(start: Long, end: Long, listOfCategoriesId: List<Int>): Float

    @Query("SELECT * FROM expenses WHERE id in (:listOfIds)")
    fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem>

    @Query("SELECT * FROM expense_categories WHERE categoryId in (SELECT categoryId FROM expenses WHERE id in (:listOfIds))")
    fun getCategoriesByExpenseItemIds(listOfIds: List<Int>): List<ExpenseCategory>

    @Query("SELECT * FROM expenses WHERE id=:id")
    fun findExpenseById(id: Int): ExpenseItem?

    @Query("SELECT * FROM expenses WHERE note LIKE '%' || :note || '%'")
    fun findExpenseByNotes(note: String): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses WHERE categoryId=:categoryId AND date BETWEEN :start AND :end ")
    fun findExpensesInTimeSpan(start: Long, end: Long, categoryId: Int): List<ExpenseItem>
}