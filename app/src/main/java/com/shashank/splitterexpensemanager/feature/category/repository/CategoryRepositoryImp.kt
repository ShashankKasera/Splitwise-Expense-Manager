package com.shashank.splitterexpensemanager.feature.category.repository

import com.shashank.splitterexpensemanager.mapper.categorymapper.CategoryListMapper
import com.shashank.splitterexpensemanager.localdb.room.dao.CategoryDao
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(
    val categoryDao: CategoryDao,
    val categoryListMapper: CategoryListMapper
) : CategoryRepository {

    override fun loadAllCategory() = categoryDao.loadAllCategory().map {
        categoryListMapper.map(it)
    }
}