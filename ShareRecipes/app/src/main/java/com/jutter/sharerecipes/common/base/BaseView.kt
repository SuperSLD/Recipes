package com.jutter.sharerecipes.common.base

import com.arellomobile.mvp.MvpView

interface BaseView: MvpView {

    fun toggleLoading(show: Boolean)
}