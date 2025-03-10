package com.example.cryptoapp.di

import com.example.cryptoapp.data.worker.ChildWorkerFactory
import com.example.cryptoapp.data.worker.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory) : ChildWorkerFactory
}