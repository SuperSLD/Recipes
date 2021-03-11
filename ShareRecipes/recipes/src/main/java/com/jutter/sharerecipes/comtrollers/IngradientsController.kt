package com.jutter.sharerecipes.comtrollers

import com.jakewharton.rxrelay3.PublishRelay
import com.jutter.sharerecipes.models.human.IngradientHuman
import io.reactivex.rxjava3.core.Observable

class IngradientsController {
    private val stateRelay = PublishRelay.create<MutableList<IngradientHuman>>()

    val state: Observable<MutableList<IngradientHuman>> = stateRelay
    fun select(ingradients: MutableList<IngradientHuman>) = stateRelay.accept(ingradients)
}