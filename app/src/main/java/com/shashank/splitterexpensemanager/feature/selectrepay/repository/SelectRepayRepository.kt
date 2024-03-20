package com.shashank.splitterexpensemanager.feature.selectrepay.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import kotlinx.coroutines.flow.Flow

interface SelectRepayRepository {

    fun loadAllGroupMemberWithGroupId(groupId: Long): Flow<List<Person>>
}