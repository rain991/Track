package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.models.currency.CurrenciesPreference
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesPreferenceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(currenciesPreference: CurrenciesPreference)

    @Update
    suspend fun update(currenciesPreference: CurrenciesPreference)

    @Delete
    suspend fun delete(currenciesPreference: CurrenciesPreference)

    @Query("SELECT * FROM currenciesPreference")
    fun getAllCurrenciesPreferences(): Flow<List<CurrenciesPreference>>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT preferableCurrency FROM currenciesPreference)")
    fun getPreferableCurrency(): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT firstAdditionalCurrency FROM currenciesPreference)")
    fun getFirstAdditionalCurrency(): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT secondAdditionalCurrency FROM currenciesPreference)")
    fun getSecondAdditionalCurrency(): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT thirdAdditionalCurrency FROM currenciesPreference)")
    fun getThirdAdditionalCurrency(): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT fourthAdditionalCurrency FROM currenciesPreference)")
    fun getFourthAdditionalCurrency(): Flow<Currency?>
}