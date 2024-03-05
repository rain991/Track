package com.example.expensetracker.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.constants.CURRENCIES_PREFERENCE_ID
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

    @Query("SELECT * FROM currenciesPreference WHERE id = :currenciesPreferenceId")
    fun getCurrenciesPreferences(currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<CurrenciesPreference>

    @Query("UPDATE currenciesPreference SET preferableCurrency=:currencyTicker WHERE id = :currenciesPreferenceId")
    suspend fun updatePreferableCurrency(currencyTicker: String, currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID)

    @Query("UPDATE currenciesPreference SET firstAdditionalCurrency=:currencyTicker WHERE id = :currenciesPreferenceId")
    suspend fun updateFirstAdditionalCurrency(currencyTicker: String, currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID)

    @Query("UPDATE currenciesPreference SET secondAdditionalCurrency=:currencyTicker WHERE id = :currenciesPreferenceId")
    suspend fun updateSecondAdditionalCurrency(currencyTicker: String, currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID)

    @Query("UPDATE currenciesPreference SET thirdAdditionalCurrency=:currencyTicker WHERE id = :currenciesPreferenceId")
    suspend fun updateThirdAdditionalCurrency(currencyTicker: String, currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID)

    @Query("UPDATE currenciesPreference SET fourthAdditionalCurrency=:currencyTicker WHERE id = :currenciesPreferenceId")
    suspend fun updateForthAdditionalCurrency(currencyTicker: String, currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID)

    @Query("SELECT * FROM currency WHERE ticker = (SELECT preferableCurrency FROM currenciesPreference WHERE id=:currenciesPreferenceId)")
    fun getPreferableCurrency(currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT firstAdditionalCurrency FROM currenciesPreference WHERE id=:currenciesPreferenceId)")
    fun getFirstAdditionalCurrency(currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT secondAdditionalCurrency FROM currenciesPreference WHERE id=:currenciesPreferenceId)")
    fun getSecondAdditionalCurrency( currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT thirdAdditionalCurrency FROM currenciesPreference WHERE id=:currenciesPreferenceId)")
    fun getThirdAdditionalCurrency(currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<Currency?>

    @Query("SELECT * FROM currency WHERE ticker = (SELECT fourthAdditionalCurrency FROM currenciesPreference WHERE id=:currenciesPreferenceId)")
    fun getFourthAdditionalCurrency(currenciesPreferenceId : Int = CURRENCIES_PREFERENCE_ID): Flow<Currency?>
}