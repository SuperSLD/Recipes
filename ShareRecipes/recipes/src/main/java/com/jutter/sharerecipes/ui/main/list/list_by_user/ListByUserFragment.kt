package com.jutter.sharerecipes.ui.main.list.list_by_user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.ListByUserHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.fragment_user_recipes.container
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_tape_shimmer.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*

class ListByUserFragment : BaseFragment(R.layout.fragment_list_by_user), ListByUserView {

    @InjectPresenter
    lateinit var presenter: ListByUserPresenter

    private var link = ""

    @ProvidePresenter
    fun providePresenter() = ListByUserPresenter(arguments?.getInt(ARG_USER_ID)!!)

    private val adapter by lazy {
        ListByUserAdapter(presenter::selectRecipes)
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.list_by_user_title_loading, R.drawable.ic_arrow_back, R.drawable.ic_reply_reverse, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }
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

    @SuppressLint("SetTextI18n")
    override fun showList(list: ListByUserHuman) {
        include_toolbar.tvTitle.text = "@${list.user.name}"
        this.link = list.user.link
        adapter.addAll(list.recipes, list.user)
        imEmpty.visibility = View.GONE
        rvRecipes.visibility = View.VISIBLE
    }

    override fun showErrorLoading() {
        loadingError.visibility = View.VISIBLE
        showShimmer(false)
        with(include_toolbar) {
            tvTitle.text = getString(R.string.list_by_user_title_error)
        }
        loadingErrorButton.setOnClickListener {
            presenter.loadList()
        }
    }

    private val shareLambda = { _: View ->
        presenter.share(link)
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            showShimmer(true)
            rvRecipes.visibility = View.GONE
            loadingError.visibility = View.GONE

            with(include_toolbar) {
                icFirst.setOnClickListener(null)
                icFirst.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorTextHint),
                        android.graphics.PorterDuff.Mode.SRC_IN
                )

                tvTitle.text = getString(R.string.list_by_user_title_loading)
            }
        } else {
            showShimmer(false)
            loadingProgressBar.visibility = View.GONE
            rvRecipes.visibility = View.VISIBLE
            loadingError.visibility = View.GONE

            with(include_toolbar) {
                icFirst.setOnClickListener(shareLambda)
                icFirst.setColorFilter(
                        ContextCompat.getColor(context, R.color.colorPrimary),
                        android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
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
        private const val ARG_USER_ID = "arg_user_id"

        fun create(
            id: Int
        ): ListByUserFragment {
            val fragment = ListByUserFragment()

            val args = Bundle()
            args.putInt(ARG_USER_ID, id)
            fragment.arguments = args

            return fragment
        }
    }
}