package com.jutter.sharerecipes.ui.main.list.holders

import android.annotation.SuppressLint
import android.view.View
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.jutter.sharerecipes.R
import kotlinx.android.synthetic.main.item_tape_header.view.*

@SuppressLint("NonConstantResourceId")
@HolderLayout(layout = R.layout.item_tape_header)
class TapeTitleHolder(itemView: View) : DFBaseHolder(itemView) {
    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        with(itemView) {
            tvTitle.text = resources.getString(data as Int)
        }
    }
}