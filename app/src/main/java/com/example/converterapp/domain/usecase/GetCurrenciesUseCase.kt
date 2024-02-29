package com.example.converterapp.domain.usecase

import com.example.converterapp.data.model.CurrencyResponse
import com.example.converterapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class GetCurrenciesUseCaseImpl @Inject constructor(
    private val currencyConverter: CurrencyRepository
): GetCurrenciesUseCase {
    override suspend operator fun invoke(
        date: String,
        apiKey: String,
        baseCurrency: String
    ) = withContext(Dispatchers.IO) {
        currencyConverter.getCurrencies(date, apiKey, baseCurrency)
    }
}

interface GetCurrenciesUseCase {
    suspend operator fun invoke(date: String, apiKey: String, baseCurrency: String): Response<CurrencyResponse>
}