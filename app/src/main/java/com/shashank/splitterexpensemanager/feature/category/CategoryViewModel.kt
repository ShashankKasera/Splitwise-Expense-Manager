package com.shashank.splitterexpensemanager.feature.category

import androidx.lifecycle.ViewModel
import com.shashank.splitterexpensemanager.feature.category.data.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    categoryRepository: CategoryRepository,
) : ViewModel() {
    var allCategoryLiveData = categoryRepository.loadAllCategory()
}