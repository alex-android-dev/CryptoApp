package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.data.Repository.CoinRepositoryImpl
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.database.CoinInfoDao
import com.example.cryptoapp.data.network.ApiFactory
import com.example.cryptoapp.data.network.ApiService
import com.example.cryptoapp.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // todo: поменять на другой
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(application: Application): CoinInfoDao =
            AppDatabase.getInstance(application).coinPriceInfoDao()

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService = ApiFactory.apiService
    }

}