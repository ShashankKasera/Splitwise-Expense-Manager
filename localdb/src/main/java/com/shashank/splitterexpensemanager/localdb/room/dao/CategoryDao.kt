package com.shashank.splitterexpensemanager.localdb.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shashank.splitterexpensemanager.localdb.model.Category

@Dao
interface CategoryDao {
    @Insert
    fun insertCategory(category: Category)

    @Update
    suspend fun upDateCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Insert
    fun insertAllCategory(vararg category: Category)

    @Query("Select * from Category")
    fun loadAllCategory(): LiveData<List<Category>>
}