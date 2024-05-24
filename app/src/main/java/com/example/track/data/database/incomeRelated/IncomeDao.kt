package com.example.track.data.database.incomeRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.domain.models.incomes.IncomeItem
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

    @Query("SELECT * FROM incomes WHERE disabled=0")
    fun getAllEnabledIncomes(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM incomes WHERE date BETWEEN :start AND :end ORDER BY date DESC")
    fun getIncomesInTimeSpanDateDecs(start: Long, end: Long): Flow<List<IncomeItem>>

    @Query("SELECT Count(value) FROM incomes WHERE date BETWEEN :start AND :end")
    fun getCountOfIncomesInTimeSpan(start: Long, end: Long): Flow<Int>

    @Query("SELECT MAX(value) FROM incomes WHERE date BETWEEN :start AND :end")
    suspend fun getBiggestIncomeInTimeSpan(start: Long, end: Long): Float?

    @Query("SELECT * FROM incomes WHERE disabled=0 ORDER BY date DESC")
    fun getAllWithDateDesc(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM incomes WHERE disabled=0 ORDER BY date ASC")
    fun getAllWithDateAsc(): Flow<List<IncomeItem>>

    @Query("SELECT * FROM incomes WHERE id=:id")
    suspend fun findIncomeById(id: Int): IncomeItem?

    @Query("SELECT * FROM incomes WHERE categoryId=:categoryId AND date BETWEEN :start AND :end ")
    suspend fun findIncomesInTimeSpanByCategory(start: Long, end: Long, categoryId: Int): List<IncomeItem>

    @Query("SELECT * FROM incomes WHERE note LIKE '%' || :note || '%'")
    fun findIncomesByNotes(note: String): Flow<List<IncomeItem>>
}