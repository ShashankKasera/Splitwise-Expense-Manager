package com.example.splitwiseexpensemanager.di

import com.example.splitwiseexpensemanager.actionprocessor.actions.LoginActionProcessor
import com.example.core.actionprocessor.Action
import com.example.core.actionprocessor.ActionType
import com.example.splitwiseexpensemanager.actionprocessor.action.RegistrationActionProcessor
import com.example.splitwiseexpensemanager.actionprocessor.actions.DashboardActionProcessor
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ActionTypeKey(val value: ActionType)

@Module
@InstallIn(ActivityComponent::class)
internal abstract class ActionsMultiBinderModule {

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.DASH_BOARD)
    internal abstract fun bindDashboardAction(action: DashboardActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.LOGIN)
    internal abstract fun bindLoginAction(action: LoginActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.REGISTRATION)
    internal abstract fun bindRegistrationAction(action: RegistrationActionProcessor): Action
}