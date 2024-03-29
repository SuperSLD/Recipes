package com.jutter.sharerecipes.common.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.google.android.material.button.MaterialButton
import com.raspisanie.mai.extesions.addSystemTopPadding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.android.synthetic.main.layout_toolbar.*

open class BaseFragment(private val layoutRes: Int) : MvpAppCompatFragment() {

    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    private val compositeDisposable = CompositeDisposable()

    protected open var bottomNavigationViewVisibility = View.VISIBLE

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
    }

    fun setTittleToolBar(appBarLayout: View, title: Int, closeIcon: Int, firstIcon: Int, secondIcon: Int) {

        with(appBarLayout) {
            addSystemTopPadding()
            if (title!= 0){
                tvTitle.text = getString(title)
            } else tvTitle.visibility = View.INVISIBLE

            if (closeIcon != 0) showIcon(icClose, closeIcon)
            if (firstIcon != 0) showIcon(icFirst, firstIcon)
            if (secondIcon != 0) showIcon(icSecond, secondIcon)
        }
    }

    private fun showIcon (imageView: MaterialButton, icon: Int): MaterialButton {

        imageView.setIconResource(icon)
        imageView.visibility = View.VISIBLE
        return imageView
    }

    protected fun showIcon (imageView: ImageView, icon: Int): ImageView {

        imageView.setImageResource(icon)
        imageView.visibility = View.VISIBLE
        return imageView
    }

    open fun onBackPressed() {}
}