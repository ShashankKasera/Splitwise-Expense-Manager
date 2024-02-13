package com.shashank.splitterexpensemanager.mapper.groupmembermapper
import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.GroupMember as GroupMemberEntity
import com.shashank.splitterexpensemanager.model.GroupMember
import javax.inject.Inject

class GroupMemberListMapper @Inject constructor(
    private val groupMember: GroupMemberMapper
) : ListMapper<GroupMemberEntity, GroupMember> {
    override fun map(input: List<GroupMemberEntity>): List<GroupMember> {
        return input.map { groupMember.map(it) }
    }
}