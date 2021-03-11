package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.ui.main.list.TapeAdapter

@HolderLayout(layout = R.layout.item_user_recipes)
class UserRecipesHolder(
        itemView: View
) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        itemView.setOnClickListener {
            makeEvent(TapeAdapter.USER_RECIPES_CLICK_EVENT)
        }
    }
}