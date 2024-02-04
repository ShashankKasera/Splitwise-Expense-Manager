package com.shashank.splitterexpensemanager.localdb.room.repository

import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CategoryRepository @Inject constructor(var categoryDao: CategoryDao) {
    suspend fun insertCategory(category: Category) = withContext(Dispatchers.IO) {
        categoryDao.insertCategory(category)
    }
    suspend fun insertAllCategory(vararg category: Category) = withContext(Dispatchers.IO) {
        categoryDao.insertAllCategory(*category)
    }
    fun loadAllCategory() = categoryDao.loadAllCategory()
}