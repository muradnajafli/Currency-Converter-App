package com.example.converterapp.presentation.converter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.converterapp.domain.repository.ConverterRepository
import com.example.converterapp.domain.usecase.ConvertCurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConverterViewModel @Inject constructor(
    private val convertCurrencyUseCase: ConvertCurrencyUseCase
) : ViewModel() {

    private val _convertedToCurrency = MutableStateFlow("USD")

    private val _conversionRate = MutableStateFlow(0.0)

    private val _convertedValue = MutableStateFlow<String?>("")
    val convertedValue = _convertedValue.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun setConvertedToCurrency(currency: String) {
        _convertedToCurrency.value = currency
        fetchConversionRate()
    }

    private fun fetchConversionRate() {
        val convertedToCurrency = _convertedToCurrency.value

        if (convertedToCurrency.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val conversionRate = convertCurrencyUseCase(convertedToCurrency)
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
        amountStr?.toFloatOrNull()?.let { amount ->
            val conversion = amount * _conversionRate.value
            _convertedValue.value = conversion.toString()
            _errorMessage.value = null
        } ?: run {
            _convertedValue.value = ""
            _errorMessage.value = "Please enter an amount"
        }
    }
}