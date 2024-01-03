package com.example.core.actionprocessor.model

import androidx.annotation.Keep
import com.example.core.actionprocessor.ActionType
import java.io.Serializable


@Keep
data class ActionParams(
    val type: ActionType,
    val data: HashMap<String, Any>? = null,
) : Serializable {
    companion object {
        const val serialVersionUID: Long = 1L
    }
}
