package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.ui.main.list.TapeAdapter
import kotlinx.android.synthetic.main.item_tape_header.view.*

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