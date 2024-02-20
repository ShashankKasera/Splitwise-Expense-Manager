package com.shashank.splitterexpensemanager.core.actionprocessor

import androidx.annotation.Keep
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams

@Keep
enum class ActionType {
    DASH_BOARD,
    LOGIN,
    REGISTRATION,
    ADD_GROUP_MEMBER,
    ADD_GROUP
}

interface Action {
    fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)? = null)
}