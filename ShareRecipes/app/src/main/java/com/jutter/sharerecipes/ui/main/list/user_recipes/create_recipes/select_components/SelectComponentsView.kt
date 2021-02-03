package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.CategoryHuman
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.server.CategoryResponse

interface SelectComponentsView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(list: MutableList<CategoryHuman>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()
}