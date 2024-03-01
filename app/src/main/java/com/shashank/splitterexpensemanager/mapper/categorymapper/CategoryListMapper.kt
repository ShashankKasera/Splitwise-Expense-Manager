package com.shashank.splitterexpensemanager.mapper.categorymapper

import com.shashank.splitterexpensemanager.core.mapper.ListMapper
import com.shashank.splitterexpensemanager.localdb.model.Category as CategoryEntity
import com.shashank.splitterexpensemanager.model.Category
import javax.inject.Inject

class CategoryListMapper @Inject constructor(
    private val categoryMapper: CategoryMapper
) : ListMapper<CategoryEntity, Category> {
    override fun map(input: List<CategoryEntity>): List<Category> {
        return input.map { categoryMapper.map(it) }
    }
}