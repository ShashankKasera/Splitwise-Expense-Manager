package com.shashank.splitterexpensemanager.feature.group.repository

import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun loadAllGroup(): Flow<List<Group>>

    fun loadAllOweByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    fun loadAllOwedByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>
}