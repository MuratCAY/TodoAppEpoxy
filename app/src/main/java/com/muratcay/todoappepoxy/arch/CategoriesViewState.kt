package com.muratcay.todoappepoxy.arch

import com.muratcay.todoappepoxy.database.entity.CategoryEntity

data class CategoriesViewState(
    val isLoading: Boolean = false,
    val itemList: List<Item> = emptyList()
) {
    data class Item(
        val categoryEntity: CategoryEntity = CategoryEntity(),
        val isSelected: Boolean = false
    )
}
