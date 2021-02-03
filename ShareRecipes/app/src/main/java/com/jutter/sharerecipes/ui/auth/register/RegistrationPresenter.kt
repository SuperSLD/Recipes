package com.jutter.sharerecipes.ui.auth.register

import android.content.Context
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.models.server.LoginBody
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.extensions.saveToken
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class RegistrationPresenter : BasePresenter<BaseView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val service: ApiService by inject()
    private val context: Context by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: BaseView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun register(login: String, password: String) {
        service.register(LoginBody(login, password))
                .map { if (it.success == true) it.data else error(it.message.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnSuccess { viewState.toggleLoading(true) }
                .doOnError {
                    viewState.toggleLoading(false)
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                .subscribe(
                        {
                            if (it != null) {
                                context.saveToken(it.token)
                                context.saveAuthState(true)
                                main()
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun main() = router?.newRootScreen(Screens.MainContainer)

    fun back() = router?.exit()
}