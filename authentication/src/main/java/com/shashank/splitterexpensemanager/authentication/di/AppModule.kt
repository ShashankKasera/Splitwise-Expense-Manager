package com.shashank.splitterexpensemanager.authentication.di

import com.shashank.splitterexpensemanager.authentication.login.repository.LoginRepository
import com.shashank.splitterexpensemanager.authentication.login.repository.LoginRepositoryImp
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepository
import com.shashank.splitterexpensemanager.authentication.registration.repository.RegistrationRepositoryImp
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
    fun getLoginRepository(repo: LoginRepositoryImp): LoginRepository = repo

    @Singleton
    @Provides
    fun getRegistrationRepository(repo: RegistrationRepositoryImp): RegistrationRepository = repo
}