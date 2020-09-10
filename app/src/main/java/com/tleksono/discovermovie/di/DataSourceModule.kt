package com.tleksono.discovermovie.di

import com.tleksono.discovermovie.common.NetworkHelper
import com.tleksono.discovermovie.data.source.remote.Apis
import com.tleksono.discovermovie.data.source.repo.AppRepository
import com.tleksono.discovermovie.data.source.repo.AppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRepository(apis: Apis, networkHelper: NetworkHelper): AppRepository {
        return AppRepositoryImpl(apis, networkHelper)
    }

}
