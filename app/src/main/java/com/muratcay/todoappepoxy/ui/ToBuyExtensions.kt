package com.muratcay.todoappepoxy.ui

import com.airbnb.epoxy.EpoxyController
import com.muratcay.todoappepoxy.ui.epoxy.models.HeaderEpoxyModel

fun EpoxyController.addHeaderModel(headerText: String) {
    HeaderEpoxyModel(headerText).id(headerText).addTo(this)
}