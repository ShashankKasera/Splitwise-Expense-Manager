package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.Group

@Dao
interface GroupDao {
    @Insert
    suspend fun insertGroup(group: Group)

    @Update
    suspend fun upDateGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

    @Insert
    fun insertAllGroup(vararg group: Group)

    @Query("Select * from `Group`")
    fun loadAllGroup(): LiveData<List<Group>>
}