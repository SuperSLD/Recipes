package com.jutter.sharerecipes.ui.auth.start

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.raspisanie.mai.common.base.BasePresenter
import org.koin.core.inject
import ru.terrakok.cicerone.Router

@InjectViewState
class StartPresenter : BasePresenter<MvpView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: MvpView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun login() = router?.navigateTo(Screens.Login)

    fun registration() = router?.navigateTo(Screens.Registration)

    fun back() = router?.exit()
}