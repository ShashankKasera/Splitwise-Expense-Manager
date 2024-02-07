package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.DefaultActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.DefaultNavigator
import com.shashank.splitterexpensemanager.actionprocessor.Navigator

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {
    @Provides
    fun provideActionProcessor(processor: DefaultActionProcessor): ActionProcessor = processor

    @Provides
    fun provideNavigator(navigator: DefaultNavigator): Navigator = navigator
}