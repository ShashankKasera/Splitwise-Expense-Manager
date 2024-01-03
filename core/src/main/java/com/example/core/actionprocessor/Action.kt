package com.example.core.actionprocessor

import androidx.annotation.Keep
import com.example.core.actionprocessor.model.ActionParams

@Keep
enum class ActionType {
    DASH_BOARD,
    LOGIN,
    REGISTRATION
}

interface Action {
    fun execute(params: ActionParams, callBack: ((Boolean) -> Unit)? = null)
}