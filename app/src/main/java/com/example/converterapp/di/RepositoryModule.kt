package com.example.converterapp.di

import com.example.converterapp.data.repository.ConverterRepositoryImpl
import com.example.converterapp.data.repository.CurrencyRepositoryImpl
import com.example.converterapp.domain.repository.ConverterRepository
import com.example.converterapp.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepository: CurrencyRepositoryImpl
    ): CurrencyRepository

    @Binds
    @Singleton
    fun bindConverterRepository(
        converterRepository: ConverterRepositoryImpl
    ): ConverterRepository

}