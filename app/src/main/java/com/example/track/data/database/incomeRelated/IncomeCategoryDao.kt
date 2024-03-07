package com.example.track.data.database.incomeRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.data.models.incomes.IncomeCategory
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
}