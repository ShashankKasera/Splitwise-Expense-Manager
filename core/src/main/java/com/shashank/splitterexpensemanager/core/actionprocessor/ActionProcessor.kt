package com.shashank.splitterexpensemanager.core.actionprocessor

import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema

interface ActionProcessor {
    fun process(actionRequestSchema: ActionRequestSchema, callBack: ((Boolean) -> Unit)? = null)
}