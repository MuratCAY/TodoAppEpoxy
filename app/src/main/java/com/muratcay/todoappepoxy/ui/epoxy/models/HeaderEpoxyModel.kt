package com.muratcay.todoappepoxy.ui.epoxy.models

import com.muratcay.todoappepoxy.R
import com.muratcay.todoappepoxy.databinding.ModelHeaderItemBinding
import com.muratcay.todoappepoxy.ui.epoxy.ViewBindingKotlinModel

data class HeaderEpoxyModel(
    val headerText: String
) : ViewBindingKotlinModel<ModelHeaderItemBinding>(R.layout.model_header_item) {

    override fun ModelHeaderItemBinding.bind() {
        textView.text = headerText
    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}