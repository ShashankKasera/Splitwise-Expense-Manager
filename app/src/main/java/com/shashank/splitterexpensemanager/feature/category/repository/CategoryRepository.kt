package com.shashank.splitterexpensemanager.feature.category.repository

import com.shashank.splitterexpensemanager.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun loadAllCategory(): Flow<List<Category>>
}