package com.example.converterapp.domain.repository

import com.example.converterapp.data.model.CurrencyResponse
import retrofit2.Response

interface CurrencyRepository {

    suspend fun getCurrencies(
        date: String, apiKey: String, baseCurrency: String
    ): Response<CurrencyResponse>

}