package com.jutter.sharerecipes.ui.global

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.jutter.sharerecipes.common.enums.BottomSheetDialogType

interface GlobalView: MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun showBottomSheet(type: BottomSheetDialogType, data: Any?)
}