package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.CategoryHuman
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import kotlinx.android.synthetic.main.fragment_select_components.*
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*
import kotlin.collections.ArrayList

class SelectComponentsFragment : BaseFragment(R.layout.fragment_select_components), SelectComponentsView {

    @InjectPresenter
    lateinit var presenter: SelectComponentsPresenter

    private var lastSearchText = ""

    private val adapter by lazy {
        SelectComponentsAdapter()
    }

    private val headerAdapter by lazy {
        HeaderComponentsAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.select_components_title, R.drawable.ic_arrow_back, 0, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }

            etSearch.setClearIcon(R.drawable.ic_clear_line)
            etSearch.onSearch {
                presenter.searchIngradients(it)
                lastSearchText = it
            }
            etSearch.onDefault {
                adapter.addData(mutableListOf())
            }
            etSearch.setHintString(getString(R.string.tapes_search_hint))
            etSearch.init()
        }

        container.addSystemBottomPadding()

        val list = arguments?.getParcelableArrayList<IngradientHuman>(ARG_INGRADIENTS)!!.toMutableList()

        btnAdd.isEnabled = list.size != 0

        adapter.setSelected(list)
        headerAdapter.addAll(list)
        showHeader(list.size)

        adapter.addData(mutableListOf())
        adapter.clickEvent {
            headerAdapter.addAll(it)
            showHeader(it.size)
            btnAdd.isEnabled = it.size != 0
        }

        headerAdapter.onDelete {
            adapter.deleteItem(it)
            showHeader(adapter.getSelected().size)
            btnAdd.isEnabled = adapter.getSelected().size != 0
        }

        with(rvHeader) {
            adapter = this@SelectComponentsFragment.headerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        with(rvCategories) {
            adapter = this@SelectComponentsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        btnAdd.setOnClickListener {
            presenter.select(adapter.getSelected())
        }
    }

    fun showHeader(size: Int) {
        if (size == 0) {
            line.visibility = View.GONE
            rvHeader.visibility = View.GONE
        } else {
            line.visibility = View.VISIBLE
            rvHeader.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun showList(list: MutableList<CategoryHuman>) {
        adapter.addData(list)
    }

    override fun showErrorLoading() {
        loadingError.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.GONE
        loadingErrorButton.setOnClickListener {
            presenter.searchIngradients(lastSearchText)
        }
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            loadingContent.visibility = View.GONE
            loadingError.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            loadingContent.visibility = View.VISIBLE
            loadingError.visibility = View.GONE
        }
    }

    companion object {
        private const val ARG_INGRADIENTS = "arg_ingradients"

        fun create(
                list: MutableList<IngradientHuman>
        ): SelectComponentsFragment {
            val fragment = SelectComponentsFragment()

            val args = Bundle()
            args.putParcelableArrayList(ARG_INGRADIENTS, ArrayList(list))
            fragment.arguments = args

            return fragment
        }
    }
}