package com.jutter.sharerecipes.ui.main.list.recipes_detail

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView

interface RecipesDetailView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLikeButton()
}