package com.jutter.sharerecipes.ui.main.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.RecommendationHuman
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_tape.*
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*

class TapeFragment : BaseFragment(R.layout.fragment_tape), TapeView {

    @InjectPresenter
    lateinit var presenter: TapePresenter

    private val adapter by lazy {
        TapeAdapter(
                presenter::searchByIngradients,
                presenter::userRecipes,
                presenter::selectRecipes
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        shimmer_view_container.addSystemTopPadding()

        containerContent.addSystemTopPadding()

        with(etSearch) {
            setClearIcon(R.drawable.ic_clear_line)
            setHintString(getString(R.string.tapes_search_hint))
            onSearch {
                presenter.searchRecipes(it)
            }
            onDefault {
                presenter.loadRecommendation()
            }
            init()
        }

        with(rvTape) {
            adapter = this@TapeFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showRecommendation(recommendationHuman: RecommendationHuman) {
        adapter.addData(recommendationHuman)
    }

    override fun showSearchResult(list: MutableList<RecipesHuman>) {
        adapter.addData(list)
    }

    override fun showErrorLoading() {
        loadingError.visibility = View.VISIBLE
        rvTape.visibility = View.GONE
        shimmer_view_container.visibility = View.GONE
        shimmer_view_container.stopShimmer()
        loadingErrorButton.setOnClickListener {
            presenter.loadRecommendation()
        }
    }

    override fun showSearchLoading(show: Boolean) {

    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            loadingError.visibility = View.GONE
            rvTape.visibility = View.GONE
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmer()
        } else {
            loadingError.visibility = View.GONE
            rvTape.visibility = View.VISIBLE
            shimmer_view_container.visibility = View.GONE
            shimmer_view_container.stopShimmer()
        }
    }
}