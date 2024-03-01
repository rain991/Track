package com.example.expensetracker.data.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.currencyfreaks.com/v2.0/"
    fun create(): CurrencyApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CurrencyApiService::class.java)
    }
}
