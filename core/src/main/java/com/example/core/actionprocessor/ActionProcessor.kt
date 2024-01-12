package com.example.core.actionprocessor

import com.example.core.actionprocessor.model.ActionRequestSchema

interface ActionProcessor {
    fun process(actionRequestSchema: ActionRequestSchema, callBack: ((Boolean) -> Unit)? = null)
}