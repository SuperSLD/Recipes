package com.jutter.sharerecipes.ui.main.list.list_by_user.holders

import android.view.View
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.main.list.list_by_user.ListByUserAdapter
import kotlinx.android.synthetic.main.fragment_profile.view.tvName
import kotlinx.android.synthetic.main.fragment_recipes_detail.view.*

@HolderLayout(layout = R.layout.item_recipes_card)
class ItemHolder(itemView: View): DFBaseHolder(itemView) {
    override fun bind (data: Any?) {
        val recipes = data as RecipesHuman
        with(itemView) {
            tvName.text = recipes.name
            tvDescription.text = recipes.description
            tvUser.text = "@${recipes.user.name}"
            tvLikeCount.text = recipes.like.toString()

            setOnClickListener {
                makeEvent(ListByUserAdapter.SELECT_RECIPES_EVENT, recipes)
            }
        }
    }
}

