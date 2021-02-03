package com.jutter.sharerecipes.ui.main.list.search_by_ingradients

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.holders.IngradientHolder

interface SearchByIngradientsView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(list: MutableList<RecipesHuman>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSelectedIngradients(ingradients: MutableList<IngradientHuman>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()
}