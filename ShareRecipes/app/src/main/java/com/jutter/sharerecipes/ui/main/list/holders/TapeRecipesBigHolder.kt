package com.jutter.sharerecipes.ui.main.list.holders

import android.view.View
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.RecipesHuman
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName
import kotlinx.android.synthetic.main.item_tape_header.view.*

class TapeRecipesBigHolder(
    itemView: View,
    private var holderFactory: TapeHolderFactory
) : BaseViewHolder(itemView) {

    override fun bind(data: Any?) {
        val recipes = data as RecipesHuman
        with(itemView) {
            tvName.text = recipes.name
            tvDescription.text = recipes.description
            tvUser.text = "@${recipes.user.name}"
            tvLikeCount.text = recipes.like.toString()

            setOnClickListener {
                holderFactory.makeEvent(TapeHolderFactory.CLICK_RECIPES_EVENT, recipes)
            }
        }
    }
}