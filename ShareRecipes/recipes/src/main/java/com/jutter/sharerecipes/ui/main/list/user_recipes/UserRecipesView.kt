package com.jutter.sharerecipes.ui.main.list.user_recipes

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman

interface UserRecipesView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(list: MutableList<RecipesHuman>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()
}