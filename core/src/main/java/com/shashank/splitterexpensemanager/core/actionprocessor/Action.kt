package com.shashank.splitterexpensemanager.core.actionprocessor

import androidx.annotation.Keep
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams

@Keep
enum class ActionType {
    DASH_BOARD,
    LOGIN,
    REGISTRATION
}

interface Action {
    fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)? = null)
}