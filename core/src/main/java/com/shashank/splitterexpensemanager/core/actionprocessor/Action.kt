package com.shashank.splitterexpensemanager.core.actionprocessor

import androidx.annotation.Keep
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams

@Keep
enum class ActionType {
    DASH_BOARD,
    LOGIN,
    REGISTRATION,
    ADD_GROUP,
    GROUP_DETAILS,
    CREATE_FRIENDS,
    ADD_FRIENDS,
    GROUP_MEMBER,
    ADD_EXPENSES,
    CATEGORY
}

interface Action {
    fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)? = null)
}