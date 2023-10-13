package com.example.converterapp.presentation.converter

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.data.remote.ApiClient
import com.example.converterapp.data.utils.API_KEY
import com.example.converterapp.domain.mapper.CurrencyMapper
import com.example.converterapp.domain.usecase.CurrencyConversionUseCase
import kotlinx.coroutines.launch
import java.util.Locale

class ConverterViewModel : ViewModel() {

    private val _baseCurrency = MutableLiveData("EUR")
    val baseCurrency: LiveData<String> get() = _baseCurrency

    private val _convertedToCurrency = MutableLiveData("USD")
    val convertedToCurrency: LiveData<String> get() = _convertedToCurrency

    private val _conversionRate = MutableLiveData<Float?>()
    val conversionRate: LiveData<Float?> get() = _conversionRate

    private val _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String> get() = _convertedValue

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val currencyConversionUseCase = CurrencyConversionUseCase()

    fun setBaseCurrency(currency: String) {
        _baseCurrency.value = currency
        fetchConversionRate()

    }

    fun setConvertedToCurrency(currency: String) {
        _convertedToCurrency.value = currency
        fetchConversionRate()
    }

    fun fetchConversionRate() {
        val baseCurrency = _baseCurrency.value
        val convertedToCurrency = _convertedToCurrency.value

        if (!baseCurrency.isNullOrEmpty() && !convertedToCurrency.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val conversionRate = currencyConversionUseCase.convertCurrency(baseCurrency, convertedToCurrency)
                    Log.i("LOG", conversionRate.toString())
                    if (conversionRate != null) {
                        _conversionRate.value = conversionRate
                    } else {
                        _errorMessage.value = "Currency conversion failed."
                    }
                } catch (e: Exception) {
                    _errorMessage.value = "Error: ${e.message}"
                }
            }
        }
    }

    fun convertValue(amountStr: String?) {
        if (!amountStr.isNullOrEmpty()) {
            val amount = amountStr.toFloat()
            val conversion = amount * (_conversionRate.value ?: 0f)
            _convertedValue.value = conversion.toString()
        } else {
            _errorMessage.value = "Please enter an amount"
        }
    }
}

