package com.shashank.splitterexpensemanager.actionprocessor

import android.content.Context
import android.content.Intent
import com.shashank.splitterexpensemanager.authentication.login.LoginActivity
import com.shashank.splitterexpensemanager.authentication.registration.RegistrationActivity
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams
import com.shashank.splitterexpensemanager.core.extension.toBundle
import com.shashank.splitterexpensemanager.feature.DashboardActivity
import com.shashank.splitterexpensemanager.feature.addexpense.AddExpensesActivity
import com.shashank.splitterexpensemanager.feature.groupmember.GroupMemberActivity
import com.shashank.splitterexpensemanager.feature.addfriends.AddFriendsActivity
import com.shashank.splitterexpensemanager.feature.addgroup.AddGroupActivity
import com.shashank.splitterexpensemanager.feature.addpayment.AddPaymentActivity
import com.shashank.splitterexpensemanager.feature.balances.BalancesActivity
import com.shashank.splitterexpensemanager.feature.category.CategoryActivity
import com.shashank.splitterexpensemanager.feature.createfriens.CreateFriendsActivity
import com.shashank.splitterexpensemanager.feature.expensesdetails.ExpensesDetailsActivity
import com.shashank.splitterexpensemanager.feature.friendsdetails.FriendsDetailsActivity
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailsActivity
import com.shashank.splitterexpensemanager.feature.groupsettings.GroupSettingsActivity
import com.shashank.splitterexpensemanager.feature.repaydetails.RepayDetailsActivity
import com.shashank.splitterexpensemanager.feature.selectrepay.SelectRepayActivity
import com.shashank.splitterexpensemanager.feature.settleup.SettleUpActivity
import com.shashank.splitterexpensemanager.feature.total.TotalActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface Navigator {
    fun navigate(route: Route, actionParams: ActionParams? = null)
}

class DefaultNavigator @Inject constructor(@ActivityContext private val context: Context) : Navigator {
    override fun navigate(route: Route, actionParams: ActionParams?) {
        val intent = when (route) {
            Route.DASH_BOARD -> Intent(context, DashboardActivity::class.java)
            Route.LOGIN -> Intent(context, LoginActivity::class.java)
            Route.REGISTRATION -> Intent(context, RegistrationActivity::class.java)
            Route.ADD_GROUP -> Intent(context, AddGroupActivity::class.java)
            Route.GROUP_DETAILS -> Intent(context, GroupDetailsActivity::class.java)
            Route.CREATE_FRIENDS -> Intent(context, CreateFriendsActivity::class.java)
            Route.ADD_FRIENDS -> Intent(context, AddFriendsActivity::class.java)
            Route.GROUP_MEMBER -> Intent(context, GroupMemberActivity::class.java)
            Route.ADD_EXPENSES -> Intent(context, AddExpensesActivity::class.java)
            Route.CATEGORY -> Intent(context, CategoryActivity::class.java)
            Route.EXPENSES_DETAILS -> Intent(context, ExpensesDetailsActivity::class.java)
            Route.GROUP_SETTING -> Intent(context, GroupSettingsActivity::class.java)
            Route.FRIENDS_DETAILS -> Intent(context, FriendsDetailsActivity::class.java)
            Route.BALANCES -> Intent(context, BalancesActivity::class.java)
            Route.SETTLE_UP -> Intent(context, SettleUpActivity::class.java)
            Route.ADD_PAYMENT -> Intent(context, AddPaymentActivity::class.java)
            Route.TOTAL -> Intent(context, TotalActivity::class.java)
            Route.SELECT_REPAY -> Intent(context, SelectRepayActivity::class.java)
            Route.REPAY_DETAILS -> Intent(context, RepayDetailsActivity::class.java)
        }.apply {
            actionParams?.data?.toBundle()?.let {
                putExtras(it)
            }
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}

enum class Route {
    DASH_BOARD,
    LOGIN,
    REGISTRATION,
    ADD_GROUP,
    GROUP_DETAILS,
    CREATE_FRIENDS,
    ADD_FRIENDS,
    GROUP_MEMBER,
    ADD_EXPENSES,
    CATEGORY,
    EXPENSES_DETAILS,
    GROUP_SETTING,
    FRIENDS_DETAILS,
    BALANCES,
    SETTLE_UP,
    ADD_PAYMENT,
    TOTAL,
    SELECT_REPAY,
    REPAY_DETAILS,
}