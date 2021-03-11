package com.jutter.sharerecipes.ui.main.list.holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.main.list.TapeAdapter
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName

@HolderLayout(layout = R.layout.item_horisontal_card_list)
class TapeRecipesScrollHolder(
    itemView: View
) : DFBaseHolder(itemView) {

    override fun bind(data: Any?) {
        val adap = HorizontalAdapter()
        adap.addAll(data as MutableList<RecipesHuman>)
        with(itemView as RecyclerView) {
            adapter = adap
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }
    }

    inner class HorizontalAdapter: RecyclerView.Adapter<BaseViewHolder>() {

        private val list = mutableListOf<RecipesHuman>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return ItemHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recipes_card_small, parent, false)
            )
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            holder.bind(if (position in list.indices) list[position] else null)
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
                    tvName.text = if (recipes.name.length < 23)
                        recipes.name else
                        "${recipes.name.substring(0, 20)}..."
                    tvDescription.text = if (recipes.description.length < 48)
                        recipes.description else
                        "${recipes.description.substring(0, 45)}..."
                    tvLikeCount.text = recipes.like.toString()

                    setOnClickListener {
                        makeEvent(TapeAdapter.CLICK_RECIPES_EVENT, recipes)
                    }
                }
            }
        }
    }
}