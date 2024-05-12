package com.example.track.data.database.incomeRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomeCategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(incomeCategory: IncomeCategory)

    @Update
    suspend fun update(incomeCategory: IncomeCategory)

    @Delete
    suspend fun delete(incomeCategory: IncomeCategory)

    @Query("SELECT * FROM income_categories")
    fun getAllIncomeCategories(): Flow<List<IncomeCategory>>

    @Query("SELECT categoryId FROM income_categories WHERE note = 'Other'")
    suspend fun getCategoryOtherId() : Int

    @Query("SELECT Count(value) FROM incomes WHERE categoryId = :categoryId AND date BETWEEN :start AND :end")
    fun countIncomesByCategoryInTimeSpan(start: Long, end: Long, categoryId : Int): Flow<Int>
}