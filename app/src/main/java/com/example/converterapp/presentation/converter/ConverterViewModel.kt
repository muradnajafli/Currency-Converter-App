package com.example.converterapp.presentation.converter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.domain.usecase.CurrencyConverter
import kotlinx.coroutines.launch

class ConverterViewModel : ViewModel() {

    private val _convertedToCurrency = MutableLiveData("USD")

    private val _conversionRate = MutableLiveData<Double?>()

    private val _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String> get() = _convertedValue

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val currencyConverter = CurrencyConverter()

    fun setConvertedToCurrency(currency: String) {
        _convertedToCurrency.value = currency
        fetchConversionRate()
    }

    private fun fetchConversionRate() {
        val convertedToCurrency = _convertedToCurrency.value

        if (!convertedToCurrency.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val conversionRate = currencyConverter.convertCurrency(convertedToCurrency)
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
            val conversion = amount * (_conversionRate.value ?: 0.0)
            _convertedValue.value = conversion.toString()
        } else {
            _errorMessage.value = "Please enter an amount"
        }
    }
}