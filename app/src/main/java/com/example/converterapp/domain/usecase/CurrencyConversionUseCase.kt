package com.example.converterapp.domain.usecase

import android.util.Log
import com.example.converterapp.data.remote.ApiClient
import com.example.converterapp.data.remote.ApiService
import com.example.converterapp.data.utils.API_KEY

class CurrencyConversionUseCase() {
    private val apiService: ApiService = ApiClient.provideApi()
    suspend fun convertCurrency(baseCurrency: String, convertedToCurrency: String): Float? {
        return try {
            val response = apiService.getRates(API_KEY, baseCurrency, convertedToCurrency)
            if (response.isSuccessful) {
                val data = response.body()
                val rates = data?.rates
                val rate = rates?.javaClass?.getField(convertedToCurrency)?.getDouble(rates)
                rate?.toFloat() ?: 0f
            } else {
                Log.i("USE_CASE", "HTTP ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.i("USE_CASE", "OTHER")
            null
        }
    }
}

