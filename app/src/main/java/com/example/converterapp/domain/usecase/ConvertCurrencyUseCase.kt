package com.example.converterapp.domain.usecase

import com.example.converterapp.domain.repository.ConverterRepository
import javax.inject.Inject

class ConvertCurrencyUseCaseImpl @Inject constructor(
    private val repository: ConverterRepository
) : ConvertCurrencyUseCase {
    override suspend operator fun invoke(
        convertedToCurrency: String
    ) = repository.convertCurrency(convertedToCurrency)
}

interface ConvertCurrencyUseCase {
    suspend operator fun invoke(
        convertedToCurrency: String
    ): Double?
}