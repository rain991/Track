package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.currency.CurrenciesRates
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesRatesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currencyRate : CurrenciesRates)

    @Update
    suspend fun update(currencyRate : CurrenciesRates)

    @Delete
    suspend fun delete(currencyRate : CurrenciesRates)

    @Query("SELECT * FROM currenciesRates")
    fun getAllData() : Flow<List<CurrenciesRates>>
}