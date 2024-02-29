package com.example.converterapp.domain.repository

import com.example.converterapp.data.model.Rates

interface ConverterRepository {
    suspend fun convertCurrency(convertedToCurrency: String): Double?
    fun formatCurrencyCode(currencyCode: String): String
    fun getRateByCurrencyCode(rates: Rates, currencyCode: String): Double?
}