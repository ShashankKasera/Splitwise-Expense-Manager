package com.shashank.splitterexpensemanager.actionprocessor.action

import com.shashank.splitterexpensemanager.actionprocessor.Navigator
import com.shashank.splitterexpensemanager.actionprocessor.Route
import com.shashank.splitterexpensemanager.core.actionprocessor.Action
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams
import javax.inject.Inject

class SelectRepayActionProcessor @Inject constructor(
    private val navigator: Navigator
) : Action {
    override fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)?) {
        navigator.navigate(Route.SELECT_REPAY, params)
    }
}