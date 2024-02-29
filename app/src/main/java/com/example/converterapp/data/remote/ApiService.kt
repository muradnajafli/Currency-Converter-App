package com.example.converterapp.data.remote

import com.example.converterapp.data.model.CurrencyResponse
import com.example.converterapp.domain.model.ConverterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("{date}")
    suspend fun getCurrenciesWithDate(
        @Path("date") date: String,
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String
    ): Response<CurrencyResponse>

    @GET("latest")
    suspend fun getCurrencyRates(
        @Query("access_key") apiKey: String,
        @Query("base") baseCurrency: String,
        @Query("symbols") convertedToCurrency: String
    ): Response<ConverterResponse>

}