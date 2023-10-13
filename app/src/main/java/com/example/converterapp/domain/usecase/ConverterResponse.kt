package com.example.converterapp.domain.usecase

import com.example.converterapp.data.model.Rates
import com.google.gson.annotations.SerializedName

data class ConverterResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Rates?
)
