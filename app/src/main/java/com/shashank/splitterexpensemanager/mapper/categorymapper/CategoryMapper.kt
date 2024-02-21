package com.shashank.splitterexpensemanager.mapper.categorymapper

import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.localdb.model.Category as CategoryEntity
import com.shashank.splitterexpensemanager.model.Category
import javax.inject.Inject
class CategoryMapper @Inject constructor() : Mapper<CategoryEntity, Category> {
    override fun map(input: CategoryEntity) = Category(
        id = input.id,
        categoryName = input.categoryName,
        categoryImage = input.categoryImage,
    )
}