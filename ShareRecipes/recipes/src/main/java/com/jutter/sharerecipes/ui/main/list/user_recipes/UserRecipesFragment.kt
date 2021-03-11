package com.jutter.sharerecipes.ui.main.list.user_recipes

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.fragment_user_recipes.container
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

class UserRecipesFragment : BaseFragment(R.layout.fragment_user_recipes), UserRecipesView {

    @InjectPresenter
    lateinit var presenter: UserRecipesPresenter

    private val adapter by lazy {
        UserRecipesAdapter(presenter::selectRecipes)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.user_recipes_title, R.drawable.ic_arrow_back, 0, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }

            etSearch.setClearIcon(R.drawable.ic_clear_line)
            etSearch.onSearch {
                presenter.findList(it)
            }
            etSearch.onDefault {
                presenter.loadList()
            }
            etSearch.setHintString(getString(R.string.tapes_search_hint))
            etSearch.init()
        }

        container.addSystemBottomPadding()

        shimmer_view_container.addSystemTopPadding()

        with(rvRecipes) {
            adapter = this@UserRecipesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        btnAddNote.setOnClickListener {
            presenter.createRecipes()
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(list: MutableList<RecipesHuman>) {
        if (list.size != 0) {
            adapter.addAll(list)
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
}