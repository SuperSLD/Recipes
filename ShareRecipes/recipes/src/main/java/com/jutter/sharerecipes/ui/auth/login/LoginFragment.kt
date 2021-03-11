package com.jutter.sharerecipes.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.base.BaseView
import com.raspisanie.mai.extesions.addSystemTopAndBottomPadding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_loading_container.*

class LoginFragment : BaseFragment(R.layout.fragment_login), BaseView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        container.addSystemTopAndBottomPadding()

        btnLogin.setOnClickListener {
            presenter.login(
                    login = etLogin.text.toString(),
                    password = etPassword.text.toString()
            )
        }
        btnLogin.isEnabled = false

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
        btnLogin.isEnabled = !etLogin.text.isNullOrEmpty() && !etPassword.text.isNullOrEmpty()
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