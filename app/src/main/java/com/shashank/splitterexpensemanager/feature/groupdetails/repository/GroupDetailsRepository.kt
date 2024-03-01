package com.shashank.splitterexpensemanager.feature.groupdetails.repository

import com.shashank.splitterexpensemanager.authentication.model.Person
import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson

interface GroupDetailsRepository {
    fun loadGroup(groupId: Long): Group

    fun loadGroupExpenses(groupId: Long): List<ExpenseWithCategoryAndPerson>

    fun loadAllOweByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    fun loadAllOwedByGroupId(groupId: Long, personId: Long): List<OweOrOwedWithPerson>

    fun loadAllGroupMemberWithGroupId(groupId: Long): List<Person>
}