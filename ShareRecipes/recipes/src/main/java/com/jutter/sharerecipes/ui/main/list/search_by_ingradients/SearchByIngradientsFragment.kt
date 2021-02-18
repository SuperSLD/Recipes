package com.jutter.sharerecipes.ui.main.list.search_by_ingradients

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.HeaderComponentsAdapter
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_select_components.*
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

class SearchByIngradientsFragment : BaseFragment(R.layout.fragment_search_by_ingradients), SearchByIngradientsView {

    @InjectPresenter
    lateinit var presenter: SearchByIngradientsPresenter

    private val adapter by lazy {
        SearchByIngradientsAdapter(presenter::selectRecipes)
    }

    private val headerAdapter by lazy {
        HeaderSearchByIngradientsAdapter(
            presenter::selectIngradients,
            presenter::removeIngradient
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.search_by_ingradients_title, R.drawable.ic_arrow_back, 0, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }

        }

        rvRecipes.addSystemBottomPadding()

        shimmer_view_container.addSystemTopPadding()

        with(rvHeader) {
            adapter = this@SearchByIngradientsFragment.headerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        showList(mutableListOf())
        with(rvRecipes) {
            adapter = this@SearchByIngradientsFragment.adapter
            layoutManager = LinearLayoutManager(context)
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

    override fun showSelectedIngradients(ingradients: MutableList<IngradientHuman>) {
        headerAdapter.addAll(ingradients)
    }

    override fun showErrorLoading() {
        imEmpty.visibility = View.GONE
        loadingError.visibility = View.VISIBLE
        showShimmer(false)
        loadingErrorButton.setOnClickListener {
            presenter.findList()
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