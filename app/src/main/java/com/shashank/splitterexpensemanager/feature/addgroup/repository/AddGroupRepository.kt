package com.shashank.splitterexpensemanager.feature.addgroup.repository

import com.shashank.splitterexpensemanager.localdb.model.Group


interface AddGroupRepository {
    suspend fun insertGroup(group: Group): Long
}