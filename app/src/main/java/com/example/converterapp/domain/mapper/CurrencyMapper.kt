package com.example.converterapp.domain.mapper

import android.graphics.Color
import com.example.converterapp.domain.model.CurrencyEntity
import com.example.converterapp.data.model.Rates
import dagger.Provides

object CurrencyMapper {
    fun mapRatesToCurrencyEntities(rates: Rates?): List<CurrencyEntity> {
        return rates?.javaClass?.declaredFields?.map { field ->
            field.isAccessible = true
            val currencyCode = field.name
            val rate = field.get(rates) as? Double ?: 0.0
            CurrencyEntity(currencyCode, rate, 0.0, Color.YELLOW)
        } ?: emptyList()
    }
}