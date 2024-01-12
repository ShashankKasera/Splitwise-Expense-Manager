package com.example.splitwiseexpensemanager.actionprocessor

import android.content.ContentValues.TAG
import android.util.Log
import com.example.core.actionprocessor.Action
import com.example.core.actionprocessor.ActionProcessor
import com.example.core.actionprocessor.ActionType
import com.example.core.actionprocessor.model.ActionParams
import com.example.core.actionprocessor.model.ActionRequestSchema
import com.example.core.extension.DaggerMap
import com.example.core.extension.EMPTY
import javax.inject.Inject

open class DefaultActionProcessor @Inject constructor(
    private val actions: DaggerMap<ActionType, Action>
) : ActionProcessor {
    override fun process(actionRequestSchema: ActionRequestSchema, callBack: ((Boolean) -> Unit)?) {
        Log.i(TAG, "KYC : actions $actions")
        try {
            val actionType = ActionType.valueOf(actionRequestSchema.action ?: String.EMPTY)
            Log.i(TAG, "KYC : actionType $actionType")
            val action = actions[actionType]
            if (action == null) {
                // developer error
                Log.e(TAG, "We don't support this action as of now!")
            } else {
                action.execute(
                    ActionParams(
                        actionType,
                        actionRequestSchema.data as HashMap<String, Any>?,
                    ),
                    callBack
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Something went wrong while processing the action! ${e.message}")
        }
    }
}