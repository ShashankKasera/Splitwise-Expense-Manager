package com.shashank.splitterexpensemanager.feature.addgroup.ui.mapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import javax.inject.Inject
import com.shashank.splitterexpensemanager.feature.addgroup.ui.model.GroupType
import com.shashank.splitterexpensemanager.localdb.model.GroupType as GroupTypeEntity

class GroupListMapper @Inject constructor(
    private val groupMapper: GroupMapper
): ListMapper<GroupTypeEntity, GroupType> {
    override fun map(input: List<GroupTypeEntity>): List<GroupType> {
        return if(input.isEmpty())
            emptyList()
        else
            input.map { groupMapper.map(it) }
    }
}