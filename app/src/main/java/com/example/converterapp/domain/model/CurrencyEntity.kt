package com.example.converterapp.domain.model

data class CurrencyEntity(
    val name: String,
    val currentRate: Double,
    var rateChange: Double,
    var color: Int
)
