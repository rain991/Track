package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: Currency)
    @Update
    suspend fun update(currency: Currency)

    @Query("UPDATE currency SET rate= :rate WHERE ticker=:currencyTicker")
    suspend fun updateRate(rate: Double, currencyTicker : String)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("SELECT * FROM currency")
    fun getAllData() : Flow<List<Currency>>
}