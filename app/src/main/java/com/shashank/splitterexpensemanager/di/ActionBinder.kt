package com.shashank.splitterexpensemanager.di

import com.shashank.splitterexpensemanager.actionprocessor.action.AddExpensesActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.AddFriendsActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.AddGroupActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.CategoryActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.CreateFriendsActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.LoginActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.Action
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.actionprocessor.action.RegistrationActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.DashboardActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.ExpensesDetailsActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.GroupDetailsActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.GroupMemberActionProcessor
import com.shashank.splitterexpensemanager.actionprocessor.action.GroupSettingsActionProcessor
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
    @ActionTypeKey(ActionType.CREATE_FRIENDS)
    internal abstract fun bindCreateFriendsAction(action: CreateFriendsActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.ADD_GROUP)
    internal abstract fun bindAddGroupAction(action: AddGroupActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.GROUP_DETAILS)
    internal abstract fun bindGroupDetailsAction(action: GroupDetailsActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.ADD_FRIENDS)
    internal abstract fun bindAddFriendsAction(action: AddFriendsActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.GROUP_MEMBER)
    internal abstract fun bindGroupMemberAction(action: GroupMemberActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.ADD_EXPENSES)
    internal abstract fun bindAddExpensesAction(action: AddExpensesActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.CATEGORY)
    internal abstract fun bindCategoryAction(action: CategoryActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.EXPENSES_DETAILS)
    internal abstract fun bindExpensesDetailsAction(action: ExpensesDetailsActionProcessor): Action

    @Binds
    @IntoMap
    @ActionTypeKey(ActionType.GROUP_SETTING)
    internal abstract fun bindGroupSettingsAction(action: GroupSettingsActionProcessor): Action
}