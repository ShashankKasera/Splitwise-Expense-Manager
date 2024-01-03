package com.example.core.actionprocessor

import com.example.core.actionprocessor.model.ActionRequestSchema

private const val TAG = "ActionProcessor"

interface ActionProcessor {
    fun process(actionRequestSchema: ActionRequestSchema, callBack: ((Boolean) -> Unit)? = null)
}

