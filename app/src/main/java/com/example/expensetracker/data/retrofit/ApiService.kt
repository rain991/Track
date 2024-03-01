package com.example.expensetracker.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("rates/latest")
    fun getLatestRates(
        @Query("apikey") apiKey: String,
        @Query("symbols") symbols: String
    ): Call<CurrencyResponse>
}