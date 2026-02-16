package com.samuelseptiano.mvvmroom.di

import com.samuelseptiano.mvvmroom.data.datasource.remote.ApiService
import com.samuelseptiano.mvvmroom.data.repository.remote.NewsRepository
import com.samuelseptiano.mvvmroom.data.repository.remote.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    /**
     * Provides AlertRepository for access api service method
     */
    @Singleton
    @Provides
    fun provideNewsRepository(
        apiService: ApiService,
    ): NewsRepository {
        return NewsRepositoryImpl(
            apiService
        )
    }


}