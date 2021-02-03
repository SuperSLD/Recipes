package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes

import android.content.Context
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.comtrollers.IngradientsController
import com.jutter.sharerecipes.extensions.mappers.toIngradientResponseList
import com.jutter.sharerecipes.extensions.mappers.toStepRequestList
import com.jutter.sharerecipes.models.server.LoginBody
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.extensions.saveToken
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.StepHuman
import com.jutter.sharerecipes.models.server.CreateRecipesBody
import com.jutter.sharerecipes.models.server.StepRequest
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.core.inject
import ru.terrakok.cicerone.Router
import timber.log.Timber

@InjectViewState
class CreateRecipesPresenter : BasePresenter<CreateRecipesView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val ingradientsController: IngradientsController by inject()

    private val service: ApiService by inject()
    private val context: Context by inject()

    private var selectedIngradients = mutableListOf<IngradientHuman>()

    private val router: Router?
        get() = navigationHolder.currentRouter

    override fun attachView(view: CreateRecipesView?) {
        super.attachView(view)
        bottomVisibilityController.hide()
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
                            viewState.selectIngradients(it)
                            selectedIngradients = it
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun createRecipes(
            name: String,
            description: String,
            recipes: String,
            steps: MutableList<StepHuman>
    ) {
        val createRecipesBody = CreateRecipesBody(
                name = name,
                description = description,
                recipes = recipes,
                ingradients = selectedIngradients.toIngradientResponseList(),
                steps = steps.toStepRequestList()
        )
        service.createRecipes(createRecipesBody)
                .map { if (it.success == true) 0 else error(it.message.toString()) }
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
                            back()
                        },
                        {
                            Timber.e(it)
                        }
                ).connect()
    }

    fun deleteComponent(ingradientHuman: IngradientHuman) {
        selectedIngradients.remove(ingradientHuman)
    }

    fun selectComponents() = router?.navigateTo(Screens.SelectComponents(selectedIngradients))

    fun main() = router?.newRootScreen(Screens.MainContainer)

    fun back() = router?.exit()
}