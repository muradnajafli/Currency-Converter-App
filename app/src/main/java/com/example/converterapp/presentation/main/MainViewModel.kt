package com.example.converterapp.presentation.main

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.data.utils.Constants
import com.example.converterapp.domain.mapper.CurrencyMapper
import com.example.converterapp.domain.model.CurrencyEntity
import com.example.converterapp.domain.usecase.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
) : ViewModel() {

    private val _currencyData = MutableStateFlow<List<CurrencyEntity>>(emptyList())
    val currencyData = _currencyData.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val currencyMapper: CurrencyMapper = CurrencyMapper

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val currentDate = dateFormat.format(Date())

    fun fetchCurrencies(apiKey: String, baseCurrency: String) {
        viewModelScope.launch {
            try {
                _loading.value = true

                val currentRatesResponse = getCurrenciesUseCase(currentDate, apiKey, baseCurrency)
                if (currentRatesResponse.isSuccessful) {
                    val currentRates = currentRatesResponse.body()?.rates

                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DATE, -1)
                    val previousDate = dateFormat.format(calendar.time)

                    val previousRatesResponse =
                        getCurrenciesUseCase(previousDate, apiKey, baseCurrency)
                    val previousRates = previousRatesResponse.body()?.rates

                    if (currentRates != null && previousRates != null) {
                        val currencyList = currencyMapper.mapRatesToCurrencyEntities(currentRates)
                        val previousList = currencyMapper.mapRatesToCurrencyEntities(previousRates)
                        currencyList.forEach { currency ->
                            val previousRate = previousList.find { it.name == currency.name }?.currentRate ?: 0.0
                            val rateChange = currency.currentRate - previousRate
                            currency.rateChange = rateChange

                            currency.color = if (rateChange >= 0) Color.parseColor(
                                Constants.MY_GREEN
                            )
                            else Color.parseColor(
                                Constants.MY_RED
                            )

                        }
                        _currencyData.value = currencyList
                    } else {
                        _error.value = "No currency data available"
                    }
                } else {
                    _error.value = "Response is not successful"
                }
            } catch (e: Exception) {
                _error.value = "An error occurred: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}