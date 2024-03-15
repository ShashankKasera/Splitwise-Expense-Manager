package com.shashank.splitterexpensemanager.mapper.groupmapper

import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.Group
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity

class GroupMapper @Inject constructor() :
    Mapper<GroupEntity?, Group> {
    override fun map(input: GroupEntity?) = Group(
        id = input?.id,
        groupName = input?.groupName ?: String.EMPTY,
        groupType = input?.groupType ?: String.EMPTY,
        groupImage = input?.groupImage ?: String.EMPTY
    )
}