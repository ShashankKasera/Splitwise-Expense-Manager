package com.shashank.splitterexpensemanager.mapper.groupmapper


import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.Group as GroupEntity
import com.shashank.splitterexpensemanager.model.Group
import javax.inject.Inject

class GroupListMapper @Inject constructor(
    private val group: GroupMapper
) : ListMapper<GroupEntity, Group> {
    override fun map(input: List<GroupEntity>): List<Group> {
        return input.map { group.map(it) }
    }
}