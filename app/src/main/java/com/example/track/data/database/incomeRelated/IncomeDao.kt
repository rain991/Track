package com.example.track.data.database.incomeRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.data.models.incomes.IncomeItem
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(incomeItem: IncomeItem)

    @Update
    suspend fun update(incomeItem: IncomeItem)

    @Delete
    suspend fun delete(incomeItem: IncomeItem)

    @Query("SELECT * FROM incomes")
    fun getAllIncomes(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM incomes ORDER BY date DESC")
    fun getAllWithDateDesc(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM Expenses ORDER BY date ASC")
    fun getAllWithDateAsc(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM incomes WHERE id=:id")
    fun findIncomeById(id: Int): IncomeItem?

    @Query("SELECT * FROM incomes WHERE categoryId=:categoryId AND date BETWEEN :start AND :end ")
    fun findIncomesInTimeSpan(start : Long, end : Long, categoryId: Int) : List<IncomeItem>

    @Query("SELECT * FROM Expenses WHERE note LIKE '%' || :note || '%'")
    fun findExpenseByNotes(note: String): Flow<List<IncomeItem>>
}