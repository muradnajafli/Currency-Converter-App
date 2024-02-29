package com.example.converterapp.data.repository

import com.example.converterapp.data.model.CurrencyResponse
import com.example.converterapp.data.remote.ApiService
import com.example.converterapp.domain.repository.CurrencyRepository
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {
    override suspend fun getCurrencies(
        date: String,
        apiKey: String,
        baseCurrency: String
    ): Response<CurrencyResponse> {
        return apiService.getCurrenciesWithDate(date, apiKey, baseCurrency)
    }
}
