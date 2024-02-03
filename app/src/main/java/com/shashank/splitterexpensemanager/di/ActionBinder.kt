package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.actionprocessor.action.AddGroupActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.AddGroupMemberActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.LoginActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.Action
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.actionprocessor.action.RegistrationActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.DashboardActionProcessor
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

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

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.ADD_GROUP_MEMBER)
    internal abstract fun bindAddGroupMemberAction(action: AddGroupMemberActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.ADD_GROUP)
    internal abstract fun bindAddGroupAction(action: AddGroupActionProcessor): Action
}