package com.muratcay.todoappepoxy.ui.home

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.airbnb.epoxy.EpoxyController
import com.muratcay.todoappepoxy.R
import com.muratcay.todoappepoxy.database.entity.ItemWithCategoryEntity
import com.muratcay.todoappepoxy.databinding.ModelEmptyStateBinding
import com.muratcay.todoappepoxy.databinding.ModelItemEntityBinding
import com.muratcay.todoappepoxy.ui.addHeaderModel
import com.muratcay.todoappepoxy.ui.epoxy.LoadingEpoxyModel
import com.muratcay.todoappepoxy.ui.epoxy.ViewBindingKotlinModel

class HomeEpoxyController(
    private val itemEntityInterface: ItemEntityInterface
) : EpoxyController() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var items : List<ItemWithCategoryEntity> = emptyList()
        set(value) {
            field = value
            isLoading = false
            requestModelBuild()
        }

    override fun buildModels() {

        if (isLoading) {
            LoadingEpoxyModel().id("loading_state").addTo(this)
            return
        }

        if (items.isEmpty()) {
            EmptyStateEpoxyModel().id("empty_state").addTo(this)
            return
        }

        var currentPriority: Int = -1
        items.sortedByDescending {
            it.itemEntity.priority
        }.forEach { item ->
            if (item.itemEntity.priority != currentPriority) {
                currentPriority = item.itemEntity.priority
                addHeaderModel(getHeaderTextForPriority(currentPriority))
            }
            ItemEntityEpoxyModel(item, itemEntityInterface).id(item.itemEntity.id).addTo(this)
        }
    }

    private fun getHeaderTextForPriority(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

    data class ItemEntityEpoxyModel(
        val item: ItemWithCategoryEntity,
        val itemEntityInterface: ItemEntityInterface
    ) : ViewBindingKotlinModel<ModelItemEntityBinding>(R.layout.model_item_entity) {

        override fun ModelItemEntityBinding.bind() {
            titleTextView.text = item.itemEntity.title

            if (item.itemEntity.description == null) {
                descriptionTextView.isGone = true
            } else {
                descriptionTextView.isVisible = true
                descriptionTextView.text = item.itemEntity.description
            }

            priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(item.itemEntity)
            }

            val colorRes = when (item.itemEntity.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.gray_700
            }

            val color = ContextCompat.getColor(root.context, colorRes)
            priorityTextView.setBackgroundColor(color)
            root.setStrokeColor(ColorStateList.valueOf(color))

            root.setOnClickListener {
                itemEntityInterface.onItemSelected(item.itemEntity)
            }
        }
    }

    class EmptyStateEpoxyModel :
        ViewBindingKotlinModel<ModelEmptyStateBinding>(R.layout.model_empty_state) {
        override fun ModelEmptyStateBinding.bind() {
            // Nothing to do at the moment
        }
    }
}