package com.shashank.splitterexpensemanager.feature.category.data

import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    var categoryDao: CategoryDao,
) {
    fun loadAllCategory() = categoryDao.loadAllCategory()
}