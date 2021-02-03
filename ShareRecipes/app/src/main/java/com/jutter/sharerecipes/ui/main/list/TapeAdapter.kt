package com.jutter.sharerecipes.ui.main.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.RecommendationHuman
import com.jutter.sharerecipes.ui.main.list.holders.TapeHolderFactory

class TapeAdapter(
        private val searchByIngradients: () -> Unit?,
        private val userRecipes: () -> Unit?,
        private val select: (RecipesHuman) -> Unit?
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val holderFactory = TapeHolderFactory()

    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return holderFactory.create(viewType, parent, null)!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(recommendation: RecommendationHuman) {
        this.list.clear()

        this.list.add(Pair(TapeHolderFactory.SEARCH_BY_INGRADIENTS_HOLDER, null))
        this.list.add(Pair(TapeHolderFactory.HEADER_HOLDER, R.string.tape_user_recipes))
        this.list.add(Pair(TapeHolderFactory.USER_RECIPES_HOLDER, null))
        this.list.add(Pair(TapeHolderFactory.HEADER_HOLDER, R.string.tape_top_recipes))
        this.list.add(Pair(TapeHolderFactory.TOP_HOLDER, recommendation.popular))
        this.list.add(Pair(TapeHolderFactory.HEADER_HOLDER, R.string.tape_rec_recipes))
        for (recipes in recommendation.recommendation) {
            this.list.add(Pair(TapeHolderFactory.REC_HOLDER, recipes))
        }

        initHolderFactory()
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<RecipesHuman>) {
        this.list.clear()

        for (recipes in list) {
            this.list.add(Pair(TapeHolderFactory.REC_HOLDER, recipes))
        }

        initHolderFactory()
        notifyDataSetChanged()
    }

    private fun initHolderFactory() {
        holderFactory.setClickEvent { i, any ->
            when (i) {
                TapeHolderFactory.USER_RECIPES_CLICK_EVENT -> {
                    userRecipes()
                }
                TapeHolderFactory.SEARCH_BY_INGRADIENTS_EVENT -> {
                    searchByIngradients()
                }
                TapeHolderFactory.CLICK_RECIPES_EVENT -> {
                    select(any as RecipesHuman)
                }
            }
        }
    }
}