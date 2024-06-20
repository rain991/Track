package com.savenko.track.data.database.ideaRelated

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.savenko.track.domain.models.idea.IncomePlans
import kotlinx.coroutines.flow.Flow

@Dao
interface IncomePlansDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(incomePlans: IncomePlans)

    @Update
    suspend fun update(incomePlans: IncomePlans)

    @Delete
    suspend fun delete(incomePlans: IncomePlans)

    @Query("SELECT * FROM incomePlans")
    fun getAllData() : Flow<List<IncomePlans>>

    @Query("SELECT Count(id) FROM incomePlans")
    suspend fun getCountOfIncomePlans(): Int
}