package com.example.converterapp.data.repository

import com.example.converterapp.data.model.CurrencyResponse
import com.example.converterapp.data.remote.ApiService
import retrofit2.Response

class CurrencyRepository(private val apiService: ApiService) {

    suspend fun getCurrencies(date: String, apiKey: String, baseCurrency: String): Response<CurrencyResponse> {
        return apiService.getCurrenciesWithDate(date, apiKey, baseCurrency)
    }
}
