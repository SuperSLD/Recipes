package com.jutter.sharerecipes.ui.photo

import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.common.enums.PickPhotoType
import com.jutter.sharerecipes.comtrollers.PickPhotoController
import com.jutter.sharerecipes.ui.photo.PhotoBSView
import com.raspisanie.mai.common.base.BasePresenter
import org.koin.core.inject

@InjectViewState
class PhotoBSPresenter : BasePresenter<PhotoBSView>() {

    private val pickPhotoController: PickPhotoController by inject()

    fun gallery() {
        pickPhotoController.pick(PickPhotoType.GALLERY)
    }

    fun camera() {
        pickPhotoController.pick(PickPhotoType.CAMERA)
    }
}