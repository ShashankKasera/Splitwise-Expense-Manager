package com.shashank.splitterexpensemanager.mapper.categorymapper

import com.shashank.splitterexpensemanager.core.extension.EMPTY
import com.shashank.splitterexpensemanager.core.mapper.Mapper
import com.shashank.splitterexpensemanager.model.Category
import javax.inject.Inject
import com.shashank.splitterexpensemanager.localdb.model.Category as CategoryEntity

class CategoryMapper @Inject constructor() : Mapper<CategoryEntity?, Category> {
    override fun map(input: CategoryEntity?) = Category(
        id = input?.id ?: -1,
        categoryName = input?.categoryName ?: String.EMPTY,
        categoryImage = input?.categoryImage ?: -1,
    )
}