package com.jutter.sharerecipes.ui.auth.start

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_start.*
import java.util.*

class StartFragment : BaseFragment(R.layout.fragment_start), MvpView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    override var bottomNavigationViewVisibility = View.GONE

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val calendar = Calendar.getInstance()

        if (calendar.get(Calendar.HOUR_OF_DAY) < 6 || calendar.get(Calendar.HOUR_OF_DAY) >= 23) {
            tvTitle.text = getString(R.string.start_title_night)
        } else if (calendar.get(Calendar.HOUR_OF_DAY) in 6..11) {
            tvTitle.text = getString(R.string.start_title_morning)
        } else if (calendar.get(Calendar.HOUR_OF_DAY) in 12..17) {
            tvTitle.text = getString(R.string.start_title_day)
        } else if (calendar.get(Calendar.HOUR_OF_DAY) in 18..22) {
            tvTitle.text = getString(R.string.start_title_evening)
        }

        btnLogin.setOnClickListener {
            presenter.login()
        }

        btnRegister.setOnClickListener {
            presenter.registration()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }
}