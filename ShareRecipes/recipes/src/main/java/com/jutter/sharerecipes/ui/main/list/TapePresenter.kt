package com.jutter.sharerecipes.ui.main.list

import android.content.Context
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.enums.DeeplinkType
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.comtrollers.DeeplinkOpenController
import com.jutter.sharerecipes.extensions.mappers.toRecipesHumanList
import com.jutter.sharerecipes.extensions.mappers.toRecommendationHuman
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import retrofit2.HttpException
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class TapePresenter : BasePresenter<TapeView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val deeplinkOpenController: DeeplinkOpenController by inject()

    private val router: Router?
        get() = navigationHolder.currentRouter

    private val mainCicerone: Cicerone<Router>?
        get() = navigationHolder.getCicerone(Screens.APP_ROUTER)

    private val service: ApiService by inject()
    private val context: Context by inject()

    override fun attachView(view: TapeView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        loadRecommendation()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenOpenDeeplink()
    }

    fun loadRecommendation() {
        service.recommendation()
            .map { if (it.success == true) it.data else error(it.message.toString()) }
            .map { it?.toRecommendationHuman() }
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
                    viewState.showRecommendation(it!!)
                },
                { err ->
                    Timber.e(err)
                    when (err) {
                        is HttpException -> {
                            Timber.d(err.code().toString())
                            if (err.code() == 401) {
                                context.saveAuthState(false)
                                mainCicerone?.router?.newRootScreen(Screens.FlowAuth)
                            }
                        }
                    }
                }
            ).connect()
    }

    fun searchRecipes(text: String) {
        service.searchRecipes(text)
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
                    viewState.showSearchResult(it!!)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    private fun listenOpenDeeplink() {
        deeplinkOpenController.state
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if (!deeplinkOpenController.isOpened) {
                                deeplinkOpenController.isOpened = true
                                when(it.first) {
                                    DeeplinkType.RECIPES -> openRecipesDeeplink(it.second as String)
                                    DeeplinkType.PROFILE -> openListByUser(it.second as Int)
                                }
                            }
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    private fun openListByUser(id: Int) = router?.navigateTo(Screens.ListByUser(id))
    private fun openRecipesDeeplink(id: String) = router?.navigateTo(Screens.RecipesDetailById(id))

    fun searchByIngradients() = router?.navigateTo(Screens.SearchByIngradients)
    fun userRecipes() = router?.navigateTo(Screens.UserRecipes)
    fun selectRecipes(recipesHuman: RecipesHuman) = router?.navigateTo(Screens.RecipesDetail(recipesHuman))

    fun back() = router?.exit()
}