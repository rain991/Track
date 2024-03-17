package com.example.track.data.database.ideaRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.track.data.models.idea.ExpenseLimits
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseLimitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(expenseLimits: ExpenseLimits)

    @Update
    suspend fun update(expenseLimits: ExpenseLimits)

    @Delete
    suspend fun delete(expenseLimits: ExpenseLimits)

    @Query("SELECT * FROM expenseLimits")
    fun getAllData() : Flow<List<ExpenseLimits>>

    @Query("SELECT SUM(value) FROM expenses WHERE date>:startDate")
    fun getExpenseSumFromDate(startDate : Long) : Float
}