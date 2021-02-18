package com.jutter.sharerecipes.ui.main.list.recipes_detail

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman

interface RecipesDetailView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLikeButton()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleRecipesLoading(show: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRecipes(recipesHuman: RecipesHuman)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()
}