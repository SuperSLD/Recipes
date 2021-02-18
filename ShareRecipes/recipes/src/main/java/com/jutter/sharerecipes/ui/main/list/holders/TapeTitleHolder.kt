package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_tape_header.view.*

@HolderLayout(layout = R.layout.item_tape_header)
class TapeTitleHolder(itemView: View) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        with(itemView) {
            tvTitle.text = resources.getString(data as Int)
        }
    }
}