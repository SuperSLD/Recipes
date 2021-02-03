package com.jutter.sharerecipes.ui.global

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.Screens.APP_ROUTER
import com.jutter.sharerecipes.common.enums.BottomSheetDialogType
import com.jutter.sharerecipes.common.base.FlowFragment
import com.jutter.sharerecipes.common.base.MvpBottomSheetDialogFragment
import com.jutter.sharerecipes.ui.photo.PhotoBSFragment
import com.raspisanie.mai.ui.global.GlobalPresenter
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowGlobalFragment : FlowFragment(APP_ROUTER), GlobalView {

    @InjectPresenter
    lateinit var presenter: GlobalPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Splash)
                )
            )
        }
    }

    /**
     * Вызов ниднего диалогового фрагмента.
     * @param type тип диалогового окна.
     * @param data данные необходимые для вызова фрагмента.
     */
    override fun showBottomSheet(type: BottomSheetDialogType, data: Any?) {
        val bottomSheet: MvpBottomSheetDialogFragment = when (type) {
            BottomSheetDialogType.SELECT_PHOTO -> PhotoBSFragment()
        }

        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}