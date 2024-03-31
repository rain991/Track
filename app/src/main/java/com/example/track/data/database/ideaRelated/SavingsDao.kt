package com.example.track.data.database.ideaRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.data.models.idea.Savings
import kotlinx.coroutines.flow.Flow

@Dao
interface SavingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savings: Savings)

    @Update
    suspend fun update(savings: Savings)

    @Delete
    suspend fun delete(savings: Savings)

    @Query("SELECT * FROM savings")
    fun getAllData() : Flow<List<Savings>>

    @Query("SELECT * FROM savings WHERE startDate>:startDate")
    fun getSavingsFromDate(startDate : Long) : Flow<Savings>

    @Query("SELECT Count(id) FROM savings")
    suspend fun getCountOfSavings(): Int
}