package com.maxidev.repostars.di

import com.maxidev.repostars.data.repository.SearchRepositoryImpl
import com.maxidev.repostars.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository
}