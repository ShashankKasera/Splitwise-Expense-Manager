package com.shashank.splitterexpensemanager.actionprocessor

import android.content.Context
import android.content.Intent
import com.shashank.splitterexpensemanager.authentication.login.LoginActivity
import com.shashank.splitterexpensemanager.authentication.registration.RegistrationActivity
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams
import com.shashank.splitterexpensemanager.core.extension.toBundle
import com.shashank.splitterexpensemanager.feature.DashboardActivity
import com.shashank.splitterexpensemanager.feature.addgroup.AddGroupActivity
import com.shashank.splitterexpensemanager.feature.addgroupmember.AddGroupMemberActivity
import com.shashank.splitterexpensemanager.feature.groupdetails.GroupDetailsActivity
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
            Route.ADD_GROUP_MEMBER -> Intent(context, AddGroupMemberActivity::class.java)
            Route.ADD_GROUP -> Intent(context, AddGroupActivity::class.java)
            Route.GROUP_DETAILS -> Intent(context, GroupDetailsActivity::class.java)
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
    ADD_GROUP_MEMBER,
    ADD_GROUP,
    GROUP_DETAILS
}