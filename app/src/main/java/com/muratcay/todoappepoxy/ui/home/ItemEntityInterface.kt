package com.muratcay.todoappepoxy.ui.home

import com.muratcay.todoappepoxy.database.entity.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
    fun onItemSelected(itemEntity: ItemEntity)
}