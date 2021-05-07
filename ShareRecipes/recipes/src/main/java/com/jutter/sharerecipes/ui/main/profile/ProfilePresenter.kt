package com.jutter.sharerecipes.ui.main.profile

import android.content.Context
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.common.CiceroneHolder
import com.jutter.sharerecipes.common.base.BottomSheetDialogController
import com.jutter.sharerecipes.common.enums.BottomSheetDialogType
import com.jutter.sharerecipes.common.enums.DeeplinkType
import com.jutter.sharerecipes.comtrollers.BottomVisibilityController
import com.jutter.sharerecipes.comtrollers.DeeplinkOpenController
import com.jutter.sharerecipes.comtrollers.PickPhotoController
import com.jutter.sharerecipes.extensions.mappers.toUserHuman
import com.jutter.sharerecipes.extensions.saveAuthState
import com.jutter.sharerecipes.server.ApiService
import com.raspisanie.mai.common.base.BasePresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.inject
import pro.midev.iprofi.common.toLatin
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.io.File


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@InjectViewState
class ProfilePresenter : BasePresenter<ProfileView>() {

    private val navigationHolder: CiceroneHolder by inject()
    private val bottomVisibilityController: BottomVisibilityController by inject()

    private val deeplinkOpenController: DeeplinkOpenController by inject()

    private val mainCicerone: Cicerone<Router>?
        get() = navigationHolder.getCicerone(containerTag = Screens.APP_ROUTER)

    private val router: Router?
        get() = navigationHolder.currentRouter

    private val service: ApiService by inject()
    private val context: Context by inject()

    private val bottomSheetDialogController: BottomSheetDialogController by inject()
    private val pickPhotoController: PickPhotoController by inject()

    override fun attachView(view: ProfileView?) {
        super.attachView(view)
        bottomVisibilityController.show()
        loadProfile()
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        listenPickPhoto()
        listenOpenDeeplink()
    }

    fun share(link: String) {
        bottomSheetDialogController.show(BottomSheetDialogType.SHARE, link)
    }

    fun photoPicker() {
        bottomSheetDialogController.show(BottomSheetDialogType.SELECT_PHOTO)
    }

    fun userRecipes() = router?.navigateTo(Screens.UserRecipes)

    fun logout() {
        service.logout()
                .map { if (it.success == true) 0 else error(it.message.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {viewState.toggleExitLoading(true)}
                .doOnError {
                    viewState.toggleExitLoading(false)
                    Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                .subscribe({
                        context.saveAuthState(false)
                        returnToAuth()
                    },
                    {
                        Timber.e(it)
                    }
                ).connect()
    }

    private fun loadProfile() {
        service.profile()
            .map { if (it.success == true) it.data else error(it.message.toString()) }
            .map { it?.toUserHuman() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {viewState.toggleLoading(true)}
            .doOnSuccess { viewState.toggleLoading(false) }
            .doOnError {
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
            .subscribe({
                viewState.showUser(it!!)
            },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    private fun returnToAuth() {
        mainCicerone?.router?.newRootScreen(Screens.FlowAuth)
    }

    fun back() = router?.exit()

    private fun listenPickPhoto() {
        pickPhotoController.state
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    viewState.pickPhoto(it)
                },
                {
                    Timber.e(it)
                }
            ).connect()
    }

    fun takePhoto(path: String) {
        val newFile = File(path)
        val requestBody = RequestBody.create(MediaType.parse("*/*"), newFile)
        val part =
            MultipartBody.Part.createFormData(
                "image",
                toLatin().convertRU(newFile.name),
                requestBody
            )
        service.uploadProfileImage(part)
            .map { if (it.success == true) it.data else error(it.message.toString()) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { viewState.toggleImageLoadingVisibility(true) }
            .doOnError {
                viewState.toggleImageLoadingVisibility(false)
                Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
            .subscribe(
                {
                    viewState.changeAvatar(it!!)
                    Toast.makeText(context, context.getText(R.string.profile_photo_is_update), Toast.LENGTH_SHORT).show()
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
}