package com.shashank.splitterexpensemanager.feature.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.splitterexpensemanager.feature.category.repository.CategoryRepository
import com.shashank.splitterexpensemanager.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {
    private val _allCategory = MutableStateFlow<List<Category>>(listOf())
    val allCategory = _allCategory.asStateFlow()
    fun allCategory() {
        viewModelScope.launch {
            categoryRepository.loadAllCategory().collect {
                _allCategory.emit(it)
            }
        }
    }
}