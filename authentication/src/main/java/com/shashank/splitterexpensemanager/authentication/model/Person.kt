package com.shashank.splitterexpensemanager.authentication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    var id: Long? = 0,
    var name: String? = "",
    var emailId: String? = "",
    var number: String? = ""
) : Parcelable