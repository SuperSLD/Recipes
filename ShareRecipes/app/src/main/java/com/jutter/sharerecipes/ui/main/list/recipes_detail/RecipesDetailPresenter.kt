package com.jutter.sharerecipes.ui.main.list.recipes_detail

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.extensions.mappers.toRecipesHumanList
import com.jutter.sharerecipes.models.server.LoginBody
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.extensions.saveToken
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.UserHuman
import com.jutter.sharerecipes.models.server.LikeBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class RecipesDetailPresenter : BasePresenter<RecipesDetailView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val service: ApiService by inject()
    private val context: Context by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: RecipesDetailView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
    }

    fun like(id: String) {
        service.like(LikeBody(id))
                .map { if (it.success == true) 0 else error(it.message.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnSuccess { viewState.toggleLoading(false) }
                .doOnError {
                    viewState.toggleLoading(false)
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                .subscribe(
                        {
                            viewState.hideLikeButton()
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun openListByUser(user: UserHuman) {
        router?.navigateTo(Screens.ListByUser(user))
    }

    fun back() = router?.exit()
}