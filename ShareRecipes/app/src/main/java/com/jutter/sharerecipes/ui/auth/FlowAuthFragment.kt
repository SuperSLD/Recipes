package com.jutter.sharerecipes.ui.auth

import android.os.Bundle
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.Screens.AUTH_ROUTER
import com.jutter.sharerecipes.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowAuthFragment : FlowFragment(AUTH_ROUTER) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Start)
                )
            )
        }
    }
}