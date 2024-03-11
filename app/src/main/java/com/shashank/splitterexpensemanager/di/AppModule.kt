package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.feature.activity.repository.ActivityRepository
import com.shashank.splitterexpensemanager.feature.activity.repository.ActivityRepositoryImp
import com.shashank.splitterexpensemanager.feature.addexpense.repository.AddExpensesRepository
import com.shashank.splitterexpensemanager.feature.addexpense.repository.AddExpensesRepositoryImp
import com.shashank.splitterexpensemanager.feature.addfriends.repository.AddFriendsRepository
import com.shashank.splitterexpensemanager.feature.addfriends.repository.AddFriendsRepositoryImp
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepository
import com.shashank.splitterexpensemanager.feature.addgroup.repository.AddGroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.addpayment.repository.AddPaymentRepository
import com.shashank.splitterexpensemanager.feature.addpayment.repository.AddPaymentRepositoryImp
import com.shashank.splitterexpensemanager.feature.balances.repository.BalancesRepository
import com.shashank.splitterexpensemanager.feature.balances.repository.BalancesRepositoryImp
import com.shashank.splitterexpensemanager.feature.category.repository.CategoryRepository
import com.shashank.splitterexpensemanager.feature.category.repository.CategoryRepositoryImp
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepository
import com.shashank.splitterexpensemanager.feature.createfriens.repository.CreateFriendsRepositoryImp
import com.shashank.splitterexpensemanager.feature.expensesdetails.repository.ExpensesDetailsRepository
import com.shashank.splitterexpensemanager.feature.expensesdetails.repository.ExpensesDetailsRepositoryImp
import com.shashank.splitterexpensemanager.feature.friends.repository.FriendsRepository
import com.shashank.splitterexpensemanager.feature.friends.repository.FriendsRepositoryImp
import com.shashank.splitterexpensemanager.feature.friendsdetails.repository.FriendsDetailsRepository
import com.shashank.splitterexpensemanager.feature.friendsdetails.repository.FriendsDetailsRepositoryImp
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepository
import com.shashank.splitterexpensemanager.feature.group.repository.GroupRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepository
import com.shashank.splitterexpensemanager.feature.groupdetails.repository.GroupDetailsRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupmember.repository.GroupMemberRepository
import com.shashank.splitterexpensemanager.feature.groupmember.repository.GroupMemberRepositoryImp
import com.shashank.splitterexpensemanager.feature.groupsettings.repository.GroupSettingsRepository
import com.shashank.splitterexpensemanager.feature.groupsettings.repository.GroupSettingsRepositoryImp
import com.shashank.splitterexpensemanager.feature.settleup.repository.SettleUpRepository
import com.shashank.splitterexpensemanager.feature.settleup.repository.SettleUpRepositoryImp
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

    @Singleton
    @Provides
    fun getExpensesDetailsRepository(repo: ExpensesDetailsRepositoryImp): ExpensesDetailsRepository = repo

    @Singleton
    @Provides
    fun getGroupSettingsRepository(repo: GroupSettingsRepositoryImp): GroupSettingsRepository = repo

    @Singleton
    @Provides
    fun getFriendsRepository(repo: FriendsRepositoryImp): FriendsRepository = repo

    @Singleton
    @Provides
    fun getFriendsDetailsRepository(repo: FriendsDetailsRepositoryImp): FriendsDetailsRepository = repo

    @Singleton
    @Provides
    fun getActivityRepository(repo: ActivityRepositoryImp): ActivityRepository = repo

    @Singleton
    @Provides
    fun getBalancesRepository(repo: BalancesRepositoryImp): BalancesRepository = repo

    @Singleton
    @Provides
    fun getSettleUpRepository(repo: SettleUpRepositoryImp): SettleUpRepository = repo

    @Singleton
    @Provides
    fun getAddPaymentRepository(repo: AddPaymentRepositoryImp): AddPaymentRepository = repo
}