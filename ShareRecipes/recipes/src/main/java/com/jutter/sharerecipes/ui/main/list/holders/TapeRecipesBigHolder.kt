package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.main.list.TapeAdapter
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName
import kotlinx.android.synthetic.main.item_tape_header.view.*

@HolderLayout(layout = R.layout.item_recipes_card)
class TapeRecipesBigHolder(
    itemView: View
) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        val recipes = data as RecipesHuman
        with(itemView) {
            tvName.text = recipes.name
            tvDescription.text = recipes.description
            tvUser.text = "@${recipes.user.name}"
            tvLikeCount.text = recipes.like.toString()

            setOnClickListener {
                makeEvent(TapeAdapter.CLICK_RECIPES_EVENT, recipes)
            }
        }
    }
}