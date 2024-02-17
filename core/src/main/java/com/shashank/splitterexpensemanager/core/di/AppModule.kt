package com.shashank.splitterexpensemanager.core.di

import com.shashank.splitterexpensemanager.core.DefaultSharedPref
import com.shashank.splitterexpensemanager.core.SharedPref
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
    fun getSharedPref(defaultSharedPref: DefaultSharedPref): SharedPref = defaultSharedPref
}