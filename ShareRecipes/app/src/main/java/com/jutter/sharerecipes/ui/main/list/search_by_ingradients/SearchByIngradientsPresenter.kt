package com.jutter.sharerecipes.ui.main.list.search_by_ingradients

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.comtrollers.IngradientsController
import com.jutter.sharerecipes.extensions.mappers.toIngradientResponseList
import com.jutter.sharerecipes.extensions.mappers.toRecipesHumanList
import com.jutter.sharerecipes.models.server.LoginBody
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.extensions.saveToken
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.server.SearchRecipesByIngradientsBody
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class SearchByIngradientsPresenter : BasePresenter<SearchByIngradientsView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val ingradientsController: IngradientsController by inject()

    private val service: ApiService by inject()
    private val context: Context by inject()

    private var selectedIngradients = mutableListOf<IngradientHuman>()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: SearchByIngradientsView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
        if (selectedIngradients.size != 0) {
            findList()
        }
    }

    override fun onFirstViewAttach() {
        listenIngradients()
    }

    private fun listenIngradients() {
        ingradientsController
            .state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.showSelectedIngradients(it)
                    selectedIngradients = it
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun findList() {
        val searchRecipesByIngradientsBody = SearchRecipesByIngradientsBody(
            ingradients = selectedIngradients.toIngradientResponseList()
        )
        service.searchRecipesByIngradients(searchRecipesByIngradientsBody)
                .map { if (it.success == true) it.data else error(it.message.toString()) }
                .map { it?.toRecipesHumanList() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { viewState.toggleLoading(true) }
                .doOnSuccess { viewState.toggleLoading(false) }
                .doOnError {
                    viewState.showErrorLoading()
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                .subscribe(
                        {
                            viewState.showList(it!!)
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun selectRecipes(recipes: RecipesHuman) {
        router?.navigateTo(Screens.RecipesDetail(recipes))
    }

    fun removeIngradient(ingradientHuman: IngradientHuman) {
        selectedIngradients.remove(ingradientHuman)
        viewState.showSelectedIngradients(selectedIngradients)
        if (selectedIngradients.size > 0) {
            findList()
        }
    }

    fun selectIngradients() {
        router?.navigateTo(Screens.SelectComponents(selectedIngradients))
    }

    fun back() = router?.exit()
}