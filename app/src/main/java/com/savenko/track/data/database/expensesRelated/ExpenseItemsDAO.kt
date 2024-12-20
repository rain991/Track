package com.savenko.track.data.database.expensesRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseItemsDAO {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(expenseItem: ExpenseItem)

    @Update
    suspend fun update(expenseItem: ExpenseItem)

    @Delete
    suspend fun deleteItem(expenseItem: ExpenseItem)

    // Retrieve expenses
    @Query("SELECT * FROM expenses")
    fun getAll(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllWithDateDesc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses ORDER BY date ASC")
    fun getAllWithDateAsc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expense_categories WHERE categoryId in (SELECT categoryId FROM expenses WHERE id in (:listOfIds))")
    fun getCategoriesByExpenseItemIds(listOfIds: List<Int>): List<ExpenseCategory>

    // In specified order

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getExpensesInTimeSpanDateDesc(start: Long, end: Long): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses WHERE date BETWEEN :start AND :end ORDER BY date ASC")
    fun getExpensesInTimeSpanDateAsc(start: Long, end: Long): Flow<List<ExpenseItem>>


    // Count of expenses
    @Query("SELECT Count(value) FROM expenses WHERE date BETWEEN :start AND :end")
    fun getCountOfExpensesInTimeSpan(start: Long, end: Long): Flow<Int>

    @Query("SELECT Count(value) FROM expenses WHERE categoryId IN (:categoriesIds)  AND date BETWEEN :start AND :end")
    fun getCountOfExpensesInTimeSpanByCategoriesIds(start: Long, end: Long, categoriesIds: List<Int>): Flow<Int>

    // In time span
    @Query("SELECT MAX(value) FROM expenses WHERE date BETWEEN :start AND :end")
    fun getBiggestExpenseInTimeSpan(start: Long, end: Long): Float?

    @Query("SELECT * FROM expenses WHERE categoryId IN (:listOfCategoriesId) AND date BETWEEN :start AND :end")
    fun getExpensesByCategoriesIdInTimeSpan(start: Long, end: Long, listOfCategoriesId: List<Int>): Flow<List<ExpenseItem>>


    // Find specified expenses
    @Query("SELECT * FROM expenses WHERE id=:id")
    fun findExpenseById(id: Int): ExpenseItem?

    @Query("SELECT * FROM expenses WHERE note LIKE '%' || :note || '%'")
    fun findExpenseByNotes(note: String): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM expenses WHERE categoryId=:categoryId AND date BETWEEN :start AND :end ")
    fun findExpensesInTimeSpan(start: Long, end: Long, categoryId: Int): List<ExpenseItem>

    @Query("SELECT * FROM expenses WHERE id in (:listOfIds)")
    fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem>
}