package com.example.splitwiseexpensemanager.actionprocessor.actions
import com.example.core.actionprocessor.Action
import com.example.core.actionprocessor.model.ActionParams
import com.example.splitwiseexpensemanager.actionprocessor.DefaultNavigator
import com.example.splitwiseexpensemanager.actionprocessor.Navigator
import com.example.splitwiseexpensemanager.actionprocessor.Route
import javax.inject.Inject

class LoginActionProcessor @Inject constructor(
    private val navigator: Navigator
): Action {
    override fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)?) {
        navigator.navigate(Route.LOGIN, params)
    }
}