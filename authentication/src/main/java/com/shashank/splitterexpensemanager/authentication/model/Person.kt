package com.shashank.splitterexpensemanager.authentication.model

import android.os.Parcelable
import com.shashank.splitterexpensemanager.core.extension.EMPTY
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var id: Long? = 0,
    var name: String? = String.EMPTY,
    var emailId: String? = String.EMPTY,
    var number: String? = String.EMPTY,
    var gender: String? = String.EMPTY
) : Parcelable