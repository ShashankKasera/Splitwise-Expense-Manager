package com.example.splitwiseexpensemanager.di

import com.example.core.actionprocessor.ActionProcessor
import com.example.splitwiseexpensemanager.actionprocessor.DefaultActionProcessor
import com.example.splitwiseexpensemanager.actionprocessor.DefaultNavigator
import com.example.splitwiseexpensemanager.actionprocessor.Navigator

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