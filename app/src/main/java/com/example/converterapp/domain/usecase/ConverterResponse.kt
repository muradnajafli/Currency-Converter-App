package com.example.converterapp.domain.usecase

import com.example.converterapp.data.model.Rates

data class ConverterResponse(
    val base: String,
    val rates: Rates
)
