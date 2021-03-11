package com.jutter.sharerecipes


import androidx.fragment.app.Fragment
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.auth.FlowAuthFragment
import com.jutter.sharerecipes.ui.auth.login.LoginFragment
import com.jutter.sharerecipes.ui.auth.register.RegistrationFragment
import com.jutter.sharerecipes.ui.splash.SplashFragment
import com.jutter.sharerecipes.ui.global.FlowGlobalFragment
import com.jutter.sharerecipes.ui.main.FlowMainFragment
import com.jutter.sharerecipes.ui.main.MainContainerFragment
import com.jutter.sharerecipes.ui.auth.start.StartFragment
import com.jutter.sharerecipes.ui.main.list.FlowTapeFragment
import com.jutter.sharerecipes.ui.main.list.TapeFragment
import com.jutter.sharerecipes.ui.main.list.list_by_user.ListByUserFragment
import com.jutter.sharerecipes.ui.main.list.recipes_detail.RecipesDetailFragment
import com.jutter.sharerecipes.ui.main.list.search_by_ingradients.SearchByIngradientsFragment
import com.jutter.sharerecipes.ui.main.list.user_recipes.UserRecipesFragment
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.CreateRecipesFragment
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.SelectComponentsFragment
import com.jutter.sharerecipes.ui.main.profile.FlowProfileFragment
import com.jutter.sharerecipes.ui.main.profile.ProfileFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    const val APP_ROUTER = "APP_ROUTER"

    object FlowGlobal : SupportAppScreen() {
        override fun getFragment() = FlowGlobalFragment()
    }

    object Splash : SupportAppScreen() {
        override fun getFragment() = SplashFragment()
    }

    const val AUTH_ROUTER = "AUTH_ROUTER"

    object FlowAuth : SupportAppScreen() {
        override fun getFragment(): Fragment? = FlowAuthFragment()
    }

    object Start: SupportAppScreen() {
        override fun getFragment(): Fragment? = StartFragment()
    }

    object Login: SupportAppScreen() {
        override fun getFragment(): Fragment? = LoginFragment()
    }

    object Registration: SupportAppScreen() {
        override fun getFragment(): Fragment? = RegistrationFragment()
    }

    const val CONTAINER_ROUTER = "CONTAINER_ROUTER"

    object FlowMain : SupportAppScreen() {
        override fun getFragment() = FlowMainFragment()
    }

    object MainContainer : SupportAppScreen() {
        override fun getFragment() = MainContainerFragment.create()
    }

    const val TAPE_ROUTER = "TAPE_ROUTER"

    object FlowTape : SupportAppScreen() {
        override fun getFragment() = FlowTapeFragment()
    }

    object Tape : SupportAppScreen() {
        override fun getFragment(): Fragment? = TapeFragment()
    }

    object UserRecipes: SupportAppScreen() {
        override fun getFragment(): Fragment? = UserRecipesFragment()
    }

    object CreateRecipes: SupportAppScreen() {
        override fun getFragment(): Fragment? = CreateRecipesFragment()
    }

    data class SelectComponents(var list: MutableList<IngradientHuman>): SupportAppScreen() {
        override fun getFragment(): Fragment? = SelectComponentsFragment.create(list)
    }

    data class RecipesDetail(var recipes: RecipesHuman): SupportAppScreen() {
        override fun getFragment(): Fragment? = RecipesDetailFragment.create(recipes)
    }

    data class RecipesDetailById(var id: String): SupportAppScreen() {
        override fun getFragment(): Fragment? = RecipesDetailFragment.create(id)
    }

    data class ListByUser(var id: Int): SupportAppScreen() {
        override fun getFragment(): Fragment? = ListByUserFragment.create(id)
    }

    object SearchByIngradients: SupportAppScreen() {
        override fun getFragment(): Fragment? = SearchByIngradientsFragment()
    }

    const val PROFILE_ROUTER = "PROFILE_ROUTER"

    object FlowProfile : SupportAppScreen() {
        override fun getFragment() = FlowProfileFragment()
    }

    object Profile : SupportAppScreen() {
        override fun getFragment(): Fragment? = ProfileFragment()
    }
}