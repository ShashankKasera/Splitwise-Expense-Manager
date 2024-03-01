package com.shashank.splitterexpensemanager.actionprocessor

import android.content.ContentValues.TAG
import android.util.Log
import com.shashank.splitterexpensemanager.core.actionprocessor.Action
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionProcessor
import com.shashank.splitterexpensemanager.core.actionprocessor.ActionType
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionParams
import com.shashank.splitterexpensemanager.core.actionprocessor.model.ActionRequestSchema
import com.shashank.splitterexpensemanager.core.extension.DaggerMap
import com.shashank.splitterexpensemanager.core.extension.EMPTY
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