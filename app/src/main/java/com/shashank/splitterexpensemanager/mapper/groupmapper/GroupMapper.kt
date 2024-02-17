package com.shashank.splitterexpensemanager.mapper.groupmapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity
import com.shashank.splitterexpensemanager.model.Group
import javax.inject.Inject

class GroupMapper @Inject constructor() :
    Mapper<GroupEntity, Group> {
    override fun map(input: GroupEntity) = Group(
        id = input.id,
        groupName = input.groupName,
        groupType = input.groupType,
        groupImage = input.groupImage
    )
}