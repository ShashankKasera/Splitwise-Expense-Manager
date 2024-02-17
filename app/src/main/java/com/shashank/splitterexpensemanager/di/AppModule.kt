package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun getAddGroupRepository(repo: AddGroupRepositoryImp): AddGroupRepository = repo
}