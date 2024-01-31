package com.shashank.splitterexpensemanager.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.localdb.model.Category
import com.shashank.splitterexpensemanager.localdb.room.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(var categoryRepository: CategoryRepository) :
    ViewModel() {
    var categoryLiveData = categoryRepository.loadAllCategory()

    suspend fun insertCategory(category: Category) = viewModelScope.launch {
        categoryRepository.insertCategory(category)
    }

    suspend fun insertAllCategory(vararg category: Category) = viewModelScope.launch {
        categoryRepository.insertAllCategory(*category)
    }
}