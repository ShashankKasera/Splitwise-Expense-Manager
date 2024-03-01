package com.shashank.splitterexpensemanager.core.actionprocessor.model

import androidx.annotation.Keep
import java.io.Serializable
@Keep
data class ActionRequestSchema(
    val action: String?,
    var data: Map<String, Any>? = null,
) : Serializable