package com.jutter.sharerecipes.ui.main.list.list_by_user

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.UserHuman
import com.jutter.sharerecipes.ui.auth.start.StartPresenter
import com.jutter.sharerecipes.ui.main.list.recipes_detail.RecipesDetailFragment
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.SelectComponentsAdapter
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopAndBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_select_components.*
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.fragment_user_recipes.container
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.item_search_line.*
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*
import java.util.*
import kotlinx.android.synthetic.main.fragment_login.container as container1
import kotlinx.android.synthetic.main.fragment_start.btnLogin as btnLogin1

class ListByUserFragment : BaseFragment(R.layout.fragment_list_by_user), ListByUserView {

    @InjectPresenter
    lateinit var presenter: ListByUserPresenter

    @ProvidePresenter
    fun providePresenter() = ListByUserPresenter(arguments?.getParcelable(ARG_USER)!!)

    private val adapter by lazy {
        ListByUserAdapter(presenter::selectRecipes)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val user = arguments?.getParcelable<UserHuman>(ARG_USER)!!

        setTittleToolBar(include_toolbar, R.string.user_recipes_title, R.drawable.ic_arrow_back, 0, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }

            tvTitle.text = "@${user.name}"
        }

        container.addSystemBottomPadding()

        shimmer_view_container.addSystemTopPadding()

        with(rvRecipes) {
            adapter = this@ListByUserFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(list: MutableList<RecipesHuman>) {
        if (list.size != 0) {
            adapter.addAll(list, list[0].user)
            imEmpty.visibility = View.GONE
            rvRecipes.visibility = View.VISIBLE
        } else {
            imEmpty.visibility = View.VISIBLE
            rvRecipes.visibility = View.GONE
        }
    }

    override fun showErrorLoading() {
        loadingError.visibility = View.VISIBLE
        showShimmer(false)
        loadingErrorButton.setOnClickListener {
            presenter.loadList()
        }
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            showShimmer(true)
            rvRecipes.visibility = View.GONE
            loadingError.visibility = View.GONE
        } else {
            showShimmer(false)
            loadingProgressBar.visibility = View.GONE
            rvRecipes.visibility = View.VISIBLE
            loadingError.visibility = View.GONE
        }
    }

    private fun showShimmer(show: Boolean) {
        if (show) {
            shimmer_view_container.startShimmer()
            shimmer_view_container.visibility = View.VISIBLE
        } else {
            shimmer_view_container.stopShimmer()
            shimmer_view_container.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_USER = "arg_user"

        fun create(
            user: UserHuman
        ): ListByUserFragment {
            val fragment = ListByUserFragment()

            val args = Bundle()
            args.putParcelable(ARG_USER, user)
            fragment.arguments = args

            return fragment
        }
    }
}