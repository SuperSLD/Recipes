package com.jutter.sharerecipes.comtrollers

import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import com.jutter.sharerecipes.common.enums.PickPhotoType
import io.reactivex.rxjava3.core.Observable

class PickPhotoController {
    private val stateRelay = PublishRelay.create<PickPhotoType>()

    val state: Observable<PickPhotoType> = stateRelay
    fun pick(pickPhotoType: PickPhotoType) = stateRelay.accept(pickPhotoType)
}