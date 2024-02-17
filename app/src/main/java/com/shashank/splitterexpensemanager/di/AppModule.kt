package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepository
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepositoryImp
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

    @Singleton
    @Provides
    fun getGroupRepository(repo: GroupRepositoryImp): GroupRepository = repo

    @Singleton
    @Provides
    fun getGroupDetailsRepository(repo: GroupDetailsRepositoryImp): GroupDetailsRepository = repo
}