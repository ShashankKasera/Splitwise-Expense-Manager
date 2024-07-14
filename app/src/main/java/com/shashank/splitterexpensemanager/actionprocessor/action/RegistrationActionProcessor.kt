package com.shashank.splitterexpensemanager.actionprocessor.action

import com.shashank.splitterexpensemanager.core.actionprocessor.Action
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams
import com.shashank.splitterexpensemanager.actionprocessor.Navigator
import com.shashank.splitterexpensemanager.actionprocessor.Route
import javax.inject.Inject

class RegistrationActionProcessor @Inject constructor(
    private val navigator: Navigator
) : Action {
    override fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)?) {
        navigator.navigate(Route.REGISTRATION, params)
    }
}