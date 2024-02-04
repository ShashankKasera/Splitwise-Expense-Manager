package com.shashank.splitterexpensemanager.feature.addgroup.ui.mapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.feature.addgroup.ui.model.GroupType
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.GroupType as GroupTypeEntity

class GroupMapper @Inject constructor(): Mapper<GroupTypeEntity, GroupType> {
    override fun map(input: GroupTypeEntity) =  GroupType (
        name = input.name,
        image = input.image
    )
}