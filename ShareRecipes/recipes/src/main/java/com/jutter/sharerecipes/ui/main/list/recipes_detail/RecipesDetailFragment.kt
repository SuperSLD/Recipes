package com.jutter.sharerecipes.ui.main.list.recipes_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_login.cvBack
import kotlinx.android.synthetic.main.fragment_recipes_detail.*
import kotlinx.android.synthetic.main.fragment_select_components.*
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.layout_loading_container.*

class RecipesDetailFragment : BaseFragment(R.layout.fragment_recipes_detail), RecipesDetailView {

    @InjectPresenter
    lateinit var presenter: RecipesDetailPresenter

    @ProvidePresenter
    fun providePresenter() = RecipesDetailPresenter(
            arguments?.getString(ARG_RECIPES_ID)
    )

    private val adapter by lazy {
        RecipesDetailAdapter()
    }

    private val adapterSteps by lazy {
        RecipesDetailStepsAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lHeader.addSystemTopPadding()
        nested.addSystemBottomPadding()

        cvBack.setOnClickListener {
            onBackPressed()
        }

        with(rvIngradients) {
            adapter = this@RecipesDetailFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        with(rvSteps) {
            adapter = this@RecipesDetailFragment.adapterSteps
            layoutManager = LinearLayoutManager(context)
        }

        val recipes = arguments?.getParcelable<RecipesHuman>(ARG_RECIPES)
        if (recipes != null) showRecipes(recipes)
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun hideLikeButton() {
        val recipes = arguments?.getParcelable<RecipesHuman>(ARG_RECIPES)!!
        recipes.like++
        tvLikeCount.text = recipes.like.toString()
        btnLike.visibility = View.GONE
    }

    override fun showErrorLoading() {
        loadingError.visibility = View.VISIBLE
        loadingProgressBar.visibility = View.GONE
        loadingErrorButton.setOnClickListener {
            presenter.loadRecipes(arguments?.getString(ARG_RECIPES_ID)!!)
        }
    }

    override fun toggleRecipesLoading(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            nested.visibility = View.GONE
            loadingError.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            nested.visibility = View.VISIBLE
            loadingError.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun showRecipes(recipes: RecipesHuman) {
        adapter.addAll(recipes.ingradients)
        adapterSteps.addAll(recipes.steps)

        tvName.text = recipes.name
        tvLikeCount.text = recipes.like.toString()
        tvUser.text = "@${recipes.user.name}"
        tvDescription.text = recipes.description
        tvRecipes.text = recipes.recipes

        btnLike.setOnClickListener {
            presenter.like(recipes.id)
        }

        btnShare.setOnClickListener {
            presenter.share(recipes.link)
        }

        btnUserList.setOnClickListener {
            presenter.openListByUser(recipes.user)
        }

    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            pbLike.visibility = View.VISIBLE
            btnLike.visibility = View.GONE
        } else {
            pbLike.visibility = View.GONE
            btnLike.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val ARG_RECIPES = "arg_recipes"
        private const val ARG_RECIPES_ID = "arg_recipes_id"

        fun create(
                recipes: RecipesHuman
        ): RecipesDetailFragment {
            val fragment = RecipesDetailFragment()

            val args = Bundle()
            args.putParcelable(ARG_RECIPES, recipes)
            fragment.arguments = args

            return fragment
        }

        fun create(
                id: String
        ): RecipesDetailFragment {
            val fragment = RecipesDetailFragment()

            val args = Bundle()
            args.putString(ARG_RECIPES_ID, id)
            fragment.arguments = args

            return fragment
        }
    }
}