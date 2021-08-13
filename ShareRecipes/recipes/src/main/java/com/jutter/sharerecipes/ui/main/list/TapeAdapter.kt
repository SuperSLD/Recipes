package com.jutter.sharerecipes.ui.main.list

import android.annotation.SuppressLint
import online.jutter.supersld.DifAdapter
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.RecommendationHuman
import com.jutter.sharerecipes.ui.main.list.holders.*

class TapeAdapter(
        private val searchByIngradients: () -> Unit?,
        private val userRecipes: () -> Unit?,
        private val select: (RecipesHuman) -> Unit?
) : DifAdapter() {

    companion object {
        const val SEARCH_BY_INGRADIENTS_HOLDER = 0
        const val HEADER_HOLDER = 1
        const val USER_RECIPES_HOLDER = 2
        const val TOP_HOLDER = 3
        const val REC_HOLDER = 4

        const val USER_RECIPES_CLICK_EVENT = 0
        const val SEARCH_BY_INGRADIENTS_EVENT = 1
        const val CLICK_RECIPES_EVENT = 2
    }

    private val list = mutableListOf<Pair<Int, Any?>>()

    override fun initFactory(): HolderFactory {
        return HolderFactory(
            hashMapOf(
                SEARCH_BY_INGRADIENTS_HOLDER to SearchByIngradientsHolder::class.java,
                HEADER_HOLDER to TapeTitleHolder::class.java,
                USER_RECIPES_HOLDER to UserRecipesHolder::class.java,
                TOP_HOLDER to TapeRecipesScrollHolder::class.java,
                REC_HOLDER to TapeRecipesBigHolder::class.java
            )
        )
        .onEvent{ id, data ->
            when(id) {
                USER_RECIPES_CLICK_EVENT -> userRecipes()
                CLICK_RECIPES_EVENT -> select(data as RecipesHuman)
                SEARCH_BY_INGRADIENTS_EVENT -> searchByIngradients()
            }
        }
    }

    override fun onBindViewHolder(holder: DFBaseHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun addData(recommendation: RecommendationHuman) {
        this.list.clear()

        this.list.add(Pair(SEARCH_BY_INGRADIENTS_HOLDER, null))
        this.list.add(Pair(HEADER_HOLDER, R.string.tape_user_recipes))
        this.list.add(Pair(USER_RECIPES_HOLDER, null))
        this.list.add(Pair(HEADER_HOLDER, R.string.tape_top_recipes))
        this.list.add(Pair(TOP_HOLDER, recommendation.popular))
        this.list.add(Pair(HEADER_HOLDER, R.string.tape_rec_recipes))
        for (recipes in recommendation.recommendation) {
            this.list.add(Pair(REC_HOLDER, recipes))
        }
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<RecipesHuman>) {
        this.list.clear()

        for (recipes in list) {
            this.list.add(Pair(REC_HOLDER, recipes))
        }
        notifyDataSetChanged()
    }
}