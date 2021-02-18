package com.jutter.sharerecipes.ui.main.profile

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.common.enums.PickPhotoType
import com.jutter.sharerecipes.models.human.UserHuman

interface ProfileView: BaseView {
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleExitLoading(show: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUser(user: UserHuman)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun toggleImageLoadingVisibility(show: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun pickPhoto(pickPhotoType: PickPhotoType)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeAvatar(path: String)
}