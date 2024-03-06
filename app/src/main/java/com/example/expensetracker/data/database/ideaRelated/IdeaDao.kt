package com.example.expensetracker.data.database.ideaRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.idea.Idea
import kotlinx.coroutines.flow.Flow

@Dao
interface IdeaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(idea: Idea)

    @Update
    suspend fun update(idea: Idea)

    @Delete
    suspend fun delete(idea: Idea)

    @Query("SELECT * FROM idea")
    fun getAllData() : Flow<List<Idea>>
}