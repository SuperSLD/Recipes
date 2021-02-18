package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.IngradientHuman

interface CreateRecipesView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun selectIngradients(ingradients: MutableList<IngradientHuman>)
}