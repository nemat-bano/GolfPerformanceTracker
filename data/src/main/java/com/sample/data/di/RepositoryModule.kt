package com.sample.data.di

import com.sample.data.MockGolfRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.GolfRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGolfRepository(
        impl: MockGolfRepository
    ): GolfRepository
}