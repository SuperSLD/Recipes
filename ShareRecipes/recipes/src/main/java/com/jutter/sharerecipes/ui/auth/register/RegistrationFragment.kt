package com.jutter.sharerecipes.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.ui.auth.start.StartPresenter
import com.raspisanie.mai.extesions.addSystemTopAndBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.layout_loading_container.*
import java.util.*
import kotlinx.android.synthetic.main.fragment_start.btnLogin as btnLogin1

class RegistrationFragment : BaseFragment(R.layout.fragment_register), BaseView {

    @InjectPresenter
    lateinit var presenter: RegistrationPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        container.addSystemTopAndBottomPadding()

        btnRegister.setOnClickListener {
            presenter.register(
                    login = etLogin.text.toString(),
                    password = etPassword.text.toString()
            )
        }
        btnRegister.isEnabled = false

        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { checkData() }
            override fun afterTextChanged(s: Editable?) {}
        }

        cvBack.setOnClickListener{
            onBackPressed()
        }

        etLogin.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)
    }

    fun checkData() {
        btnRegister.isEnabled = !etLogin.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty() &&
                etPassword.text.toString().length >= 6
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            loginForm.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            loginForm.visibility = View.VISIBLE
        }
    }
}