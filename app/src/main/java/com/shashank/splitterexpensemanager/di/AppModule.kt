package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.feature.addfriends.repository.AddFriendsRepository
import com.shashank.splitterexpensemanager.feature.addfriends.repository.AddFriendsRepositoryImp
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.category.repository.CategoryRepository
import com.shashank.splitterexpensemanager.feature.category.repository.CategoryRepositoryImp
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepository
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepositoryImp
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepository
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupmember.repository.GroupMemberRepository
import com.shashank.splitterexpensemanager.feature.groupmember.repository.GroupMemberRepositoryImp
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
    fun getAddExpensesRepository(repo: AddExpensesRepositoryImp): AddExpensesRepository = repo

    @Singleton
    @Provides
    fun getCategoryRepository(repo: CategoryRepositoryImp): CategoryRepository = repo

    @Singleton
    @Provides
    fun getGroupMemberRepository(repo: GroupMemberRepositoryImp): GroupMemberRepository = repo

    @Singleton
    @Provides
    fun getAddFriendsRepository(repo: AddFriendsRepositoryImp): AddFriendsRepository = repo

    @Singleton
    @Provides
    fun getGroupRepository(repo: GroupRepositoryImp): GroupRepository = repo

    @Singleton
    @Provides
    fun getCreateFriendsRepository(repo: CreateFriendsRepositoryImp): CreateFriendsRepository = repo

    @Singleton
    @Provides
    fun getGroupDetailsRepository(repo: GroupDetailsRepositoryImp): GroupDetailsRepository = repo

    @Singleton
    @Provides
    fun getAddGroupRepository(repo: AddGroupRepositoryImp): AddGroupRepository = repo
}