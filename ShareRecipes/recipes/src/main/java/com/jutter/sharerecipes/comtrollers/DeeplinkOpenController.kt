package com.jutter.sharerecipes.comtrollers

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jutter.sharerecipes.common.enums.DeeplinkType
import io.reactivex.rxjava3.core.Observable

class DeeplinkOpenController {
    private val stateRelay = BehaviorRelay.create<Pair<DeeplinkType, Any?>>()

    public var isOpened = false

    val state: Observable<Pair<DeeplinkType, Any?>> = stateRelay
    fun open(data: Pair<DeeplinkType, Any?>) = stateRelay.accept(data)
}