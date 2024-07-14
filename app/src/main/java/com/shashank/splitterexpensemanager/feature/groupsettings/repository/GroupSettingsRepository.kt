package com.shashank.splitterexpensemanager.feature.groupsettings.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity

interface GroupSettingsRepository {

    fun loadGroup(groupId: Long): Group

    fun loadAllGroupMemberWithGroupId(groupId: Long): List<Person>
    suspend fun deleteGroup(group: GroupEntity)

    fun loadAllOweOwedByGroupId(groupId: Long): List<OweOrOwedWithPerson>
}