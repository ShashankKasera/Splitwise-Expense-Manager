package com.shashank.splitterexpensemanager.mapper.groupmembermapper
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.localdb.model.GroupMember as GroupMemberEntity
import com.shashank.splitterexpensemanager.model.GroupMember
import javax.inject.Inject

class GroupMemberMapper @Inject constructor() : Mapper<GroupMemberEntity, GroupMember> {
    override fun map(input: GroupMemberEntity) = GroupMember(
        id = input.id,
        personId = input.personId,
        groupId = input.groupId,
    )
}