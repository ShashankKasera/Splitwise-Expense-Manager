package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.feature.addgroup.data.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.data.AddGroupRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAddGroupRepository(repository: AddGroupRepositoryImpl): AddGroupRepository =
        repository
}