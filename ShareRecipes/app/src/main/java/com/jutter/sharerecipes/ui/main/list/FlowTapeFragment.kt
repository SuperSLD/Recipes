package com.jutter.sharerecipes.ui.main.list

import android.os.Bundle
import com.jutter.sharerecipes.Screens
import com.jutter.sharerecipes.Screens.TAPE_ROUTER
import com.jutter.sharerecipes.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowTapeFragment : FlowFragment(TAPE_ROUTER) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Tape)
                )
            )
        }
    }
}