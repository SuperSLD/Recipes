package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.ui.main.list.TapeAdapter

@HolderLayout(layout = R.layout.item_search_button)
class SearchByIngradientsHolder(
        itemView: View
) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        itemView.setOnClickListener {
            makeEvent(TapeAdapter.SEARCH_BY_INGRADIENTS_EVENT)
        }
    }
}