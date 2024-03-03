package com.example.expensetracker.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("v2.0/rates/latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String,
        @Query("symbols") symbols: String
    ): CurrencyResponse
}