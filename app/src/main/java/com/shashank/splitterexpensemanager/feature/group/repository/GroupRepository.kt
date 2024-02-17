package com.shashank.splitterexpensemanager.feature.group.repository

import com.shashank.splitterexpensemanager.model.Group
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    fun loadAllGroup(): Flow<List<Group>>
}