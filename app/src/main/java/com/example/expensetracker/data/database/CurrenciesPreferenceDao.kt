package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.currency.CurrenciesPreference
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesPreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // WARNING, not sure
    suspend fun insert(currenciesPreference: CurrenciesPreference)

    @Update
    suspend fun update(currenciesPreference: CurrenciesPreference)

    @Delete
    suspend fun delete(currenciesPreference: CurrenciesPreference)

    @Query("SELECT * FROM currenciesPreference")
    fun getAllCurrenciesPreferences(): Flow<List<CurrenciesPreference>>

}