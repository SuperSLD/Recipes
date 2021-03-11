package com.jutter.sharerecipes.ui.main.list.search_by_ingradients

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.RecipesHuman
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName

class SearchByIngradientsAdapter (
        private var selectRecipes: (RecipesHuman) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<RecipesHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipes_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun addAll(list: MutableList<RecipesHuman>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        override fun bind (data: Any?) {
            val recipes = data as RecipesHuman
            with(itemView) {
                tvName.text = recipes.name
                tvDescription.text = recipes.description
                tvUser.text = "@${recipes.user.name}"
                tvLikeCount.text = recipes.like.toString()

                setOnClickListener {
                    selectRecipes(recipes)
                }
            }
        }
    }

}

