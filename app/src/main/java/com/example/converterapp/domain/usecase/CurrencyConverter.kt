package com.example.converterapp.domain.usecase

import android.util.Log
import com.example.converterapp.data.model.Rates
import com.example.converterapp.data.remote.ApiClient
import com.example.converterapp.data.remote.ApiService
import com.example.converterapp.data.utils.API_KEY

class CurrencyConverter {
    private val apiService: ApiService = ApiClient.provideApi()

    suspend fun convertCurrency(convertedToCurrency: String): Double? {
        val formattedCurrency = formatCurrencyCode(convertedToCurrency)
        return try {
            val response = apiService.getCurrencyRates(API_KEY, "EUR", formattedCurrency)
            if (response.isSuccessful) {
                val data = response.body()
                val rates = data?.rates
                val rate: Double? = rates?.let { getRateByCurrencyCode(it, formattedCurrency) }
                rate ?: 0.0
            } else {
                Log.i("USE_CASE", "HTTP ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("USE_CASE", "Error: ${e.message}", e)
            null
        }
    }

    private fun formatCurrencyCode(currencyCode: String): String {
        return currencyCode.substring(0, 1).lowercase() + currencyCode.substring(1)
    }

    private fun getRateByCurrencyCode(rates: Rates, currencyCode: String): Double? {
        val field = rates.javaClass.getDeclaredField(currencyCode)
        field.isAccessible = true
        return field.get(rates) as? Double
    }
}