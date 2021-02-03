package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.sharerecipes.common.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_tape_header.view.*

class UserRecipesHolder(
        itemView: View,
        var holderFactory: TapeHolderFactory
) : BaseViewHolder(itemView) {

    override fun bind(data: Any?) {
        itemView.setOnClickListener {
            holderFactory.makeEvent(TapeHolderFactory.USER_RECIPES_CLICK_EVENT)
        }
    }
}