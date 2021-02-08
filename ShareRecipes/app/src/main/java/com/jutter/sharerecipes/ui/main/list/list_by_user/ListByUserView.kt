package com.jutter.sharerecipes.ui.main.list.list_by_user

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.ListByUserHuman
import com.jutter.sharerecipes.models.human.RecipesHuman

interface ListByUserView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showList(list: ListByUserHuman)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showErrorLoading()
}