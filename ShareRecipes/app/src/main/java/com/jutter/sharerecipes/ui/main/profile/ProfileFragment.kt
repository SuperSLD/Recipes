package com.jutter.sharerecipes.ui.main.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.enums.PickPhotoType
import com.jutter.sharerecipes.models.human.UserHuman
import com.kbeanie.multipicker.api.CameraImagePicker
import com.kbeanie.multipicker.api.ImagePicker
import com.kbeanie.multipicker.api.Picker
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback
import com.kbeanie.multipicker.api.entity.ChosenImage
import com.raspisanie.mai.extesions.addSystemTopPadding
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*
import timber.log.Timber

class ProfileFragment : BaseFragment(R.layout.fragment_profile), ProfileView, ImagePickerCallback {

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    private val rxPermissions by lazy { RxPermissions(this) }

    private val imagePicker by lazy { ImagePicker(this) }
    private val cameraPicker by lazy { CameraImagePicker(this) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nested.addSystemTopPadding()

        btnMyRecipes.setOnClickListener {
            presenter.userRecipes()
        }

        btnLogout.setOnClickListener {
            presenter.logout()
        }

        ivAvatar.setOnClickListener { checkStoragePermission() }

        imagePicker.setImagePickerCallback(this)
        cameraPicker.setImagePickerCallback(this)
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun toggleExitLoading(show: Boolean) {
        if (show) {
            btnLogout.visibility = View.VISIBLE
            exitLoading.visibility = View.GONE
        } else {
            btnLogout.visibility = View.GONE
            exitLoading.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showUser(user: UserHuman) {
        tvName.text = "@${user.name}"
        changeAvatar(user.image)
    }

    private fun checkStoragePermission() {
        rxPermissions
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe {
                if (it) {
                    presenter.photoPicker()
                }
            }
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            showShimmer(true)
            loadingContent.visibility = View.GONE
        } else {
            showShimmer(false)
            loadingContent.visibility = View.VISIBLE
        }
    }

    private fun showShimmer(show: Boolean) {
        if (show) {
            shimmer_view_container.startShimmer()
            shimmer_view_container.visibility = View.VISIBLE
        } else {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
        }
    }

    /*
    Функции для выбора аватарки.
     */
    override fun onError(error: String?) {
        Timber.e(error)
    }

    override fun toggleImageLoadingVisibility(show: Boolean) {
        if (show) {
            imageLoadProgressBar.visibility = View.VISIBLE
            ivAvatar.visibility = View.GONE
        } else {
            imageLoadProgressBar.visibility = View.GONE
            ivAvatar.visibility = View.VISIBLE
        }
    }

    override fun onImagesChosen(p0: MutableList<ChosenImage>?) {
        p0?.let {
            if (it.isNotEmpty()) {
                presenter.takePhoto(p0[0].originalPath)
                Timber.d(p0[0].originalPath)
            }
        }
    }

    override fun pickPhoto(pickPhotoType: PickPhotoType) {
        when (pickPhotoType) {
            PickPhotoType.GALLERY -> imagePicker.pickImage()
            PickPhotoType.CAMERA -> cameraPicker.pickImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Picker.PICK_IMAGE_DEVICE -> {
                    imagePicker.submit(data)
                }
                Picker.PICK_IMAGE_CAMERA -> {
                    cameraPicker.submit(data)
                }
            }
        }
    }

    override fun changeAvatar(path: String) {
        toggleImageLoadingVisibility(true)
        Timber.d(path)
        Glide
                .with(this)
                .load(path)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                    ): Boolean {
                        toggleImageLoadingVisibility(false)
                        return false
                    }

                    override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                    ): Boolean {
                        toggleImageLoadingVisibility(false)
                        return false
                    }
                })
                .into(ivAvatar)
    }
}