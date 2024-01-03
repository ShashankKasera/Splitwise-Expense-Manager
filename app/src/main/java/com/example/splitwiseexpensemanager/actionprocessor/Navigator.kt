package com.example.splitwiseexpensemanager.actionprocessor

import android.content.Context
import android.content.Intent
import com.example.authentication.MainActivity
import com.example.authentication.login.LoginActivity
import com.example.authentication.registration.RegistrationActivity
import com.example.core.actionprocessor.model.ActionParams
import com.example.core.extension.toBundle
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

interface Navigator {
    fun navigate(route: Route, actionParams: ActionParams? = null)
}

class DefaultNavigator @Inject constructor(@ActivityContext private val context: Context) : Navigator {
    override fun navigate(route: Route, actionParams: ActionParams?) {
        val intent = when (route) {
            Route.DASH_BOARD -> Intent(context, MainActivity::class.java)
            Route.LOGIN -> Intent(context, LoginActivity::class.java)
            Route.REGISTRATION -> Intent(context, RegistrationActivity::class.java)
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
    REGISTRATION
}