package com.example.converterapp.di

import com.example.converterapp.domain.repository.ConverterRepository
import com.example.converterapp.domain.repository.CurrencyRepository
import com.example.converterapp.domain.usecase.ConvertCurrencyUseCase
import com.example.converterapp.domain.usecase.ConvertCurrencyUseCaseImpl
import com.example.converterapp.domain.usecase.GetCurrenciesUseCase
import com.example.converterapp.domain.usecase.GetCurrenciesUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun getCurrenciesUseCase(
        repository: CurrencyRepository
    ): GetCurrenciesUseCase {
        return GetCurrenciesUseCaseImpl(repository)
    }

    @Provides
    @ViewModelScoped
    fun convertCurrencyUseCase(
        repository: ConverterRepository
    ): ConvertCurrencyUseCase {
        return ConvertCurrencyUseCaseImpl(repository)
    }
}
