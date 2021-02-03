package com.jutter.sharerecipes.ui.photo

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.MvpBottomSheetDialogFragment
import kotlinx.android.synthetic.main.bs_photo.*

class PhotoBSFragment : MvpBottomSheetDialogFragment(R.layout.bs_photo), PhotoBSView {
    @InjectPresenter
    lateinit var presenter: PhotoBSPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnGallery.setOnClickListener {
            presenter.gallery()
            dismiss()
        }

        btnCamera.setOnClickListener {
            presenter.camera()
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}