package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseItemsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(expenseItem: ExpenseItem)

    @Update
    suspend fun update(expenseItem: ExpenseItem)

    @Delete
    suspend fun deleteItem(expenseItem: ExpenseItem)

    @Query("SELECT * FROM Expenses")
    fun getAll(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM Expenses ORDER BY date DESC")
    fun getAllWithDateDesc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM Expenses ORDER BY date ASC")
    fun getAllWithDateAsc(): Flow<List<ExpenseItem>>

    @Query("SELECT * FROM Expenses WHERE id=:id")
    fun findExpenseById(id: Int): ExpenseItem?

    @Query("SELECT * FROM Expenses WHERE note LIKE '%' || :note || '%'")
    fun findExpenseByNotes(note: String): Flow<ExpenseItem>

    @Query("SELECT * FROM Expenses WHERE categoryId=:categoryId AND date BETWEEN :start AND :end ")
    fun findExpensesInTimeSpan(start : Long, end : Long, categoryId: Int) : List<ExpenseItem>

//    @Query("UPDATE Expenses SET ")
//    fun setNewCurrencyById()
}