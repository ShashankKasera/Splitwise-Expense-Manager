package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.authentication.registration.repository.PersonRepository
import com.shashank.splitterexpensemanager.authentication.registration.repository.PersonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providePersonRepository(repository: PersonRepositoryImpl): PersonRepository =
        repository
}