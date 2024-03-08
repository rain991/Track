package com.example.track.data.retrofit
import com.example.track.data.constants.CURRENCY_CALL_URL_DEFAULT
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = CURRENCY_CALL_URL_DEFAULT
    val api : CurrencyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CurrencyApiService::class.java)
    }
}
