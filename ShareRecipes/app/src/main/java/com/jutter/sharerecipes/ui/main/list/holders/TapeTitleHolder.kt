package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.sharerecipes.common.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_tape_header.view.*

class TapeTitleHolder(itemView: View) : BaseViewHolder(itemView) {

    override fun bind(data: Any?) {
        with(itemView) {
            tvTitle.text = resources.getString(data as Int)
        }
    }
}