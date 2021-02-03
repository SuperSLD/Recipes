package com.jutter.sharerecipes.ui.main.list

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.RecommendationHuman

interface TapeView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRecommendation(recommendationHuman: RecommendationHuman)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSearchResult(list: MutableList<RecipesHuman>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSearchLoading(show: Boolean)
}