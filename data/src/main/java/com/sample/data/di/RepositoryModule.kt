package com.sample.data.di

import com.sample.data.repo.OfflineFirstGolfRepositoryImpl
import com.sample.data.repo.RemoteGolfRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.GolfRepository
import repository.OfflineFirstGolfRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

//    @Binds
//    @Singleton
//    abstract fun bindGolfRepository(
//        impl: MockGolfRepository
//    ): GolfRepository

    @Binds
    @Singleton
    abstract fun bindGolfRepository(
        impl: RemoteGolfRepository
    ): GolfRepository

    @Binds
    @Singleton
    abstract fun bindOfflineFirstGolfRepository(
        impl: OfflineFirstGolfRepositoryImpl
    ): OfflineFirstGolfRepository
}