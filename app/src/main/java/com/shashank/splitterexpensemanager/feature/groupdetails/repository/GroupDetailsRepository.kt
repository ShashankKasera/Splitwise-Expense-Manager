package com.shashank.splitterexpensemanager.feature.groupdetails.repository

import com.shashank.splitterexpensemanager.model.ExpenseWithCategoryAndPerson
import com.shashank.splitterexpensemanager.model.Group
import com.shashank.splitterexpensemanager.model.OweOrOwedWithPerson
import kotlinx.coroutines.flow.Flow

interface GroupDetailsRepository {
    fun loadGroup(groupId: Long): Flow<Group>
    fun loadGroupExpenses(groupId: Long): Flow<List<ExpenseWithCategoryAndPerson>>
    fun loadAllOweByGroupId(groupId: Long,personId:Long): Flow<List<OweOrOwedWithPerson>>

    fun loadAllOwedByGroupId(groupId: Long,personId:Long): Flow<List<OweOrOwedWithPerson>>
}