package com.jutter.sharerecipes.ui.main.list.recipes_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.common.base.BaseView
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import com.raspisanie.mai.extesions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_login.cvBack
import kotlinx.android.synthetic.main.fragment_recipes_detail.*
import kotlinx.android.synthetic.main.fragment_user_recipes.*
import kotlinx.android.synthetic.main.layout_loading_container.*

class RecipesDetailFragment : BaseFragment(R.layout.fragment_recipes_detail), RecipesDetailView {

    @InjectPresenter
    lateinit var presenter: RecipesDetailPresenter

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

        val recipes = arguments?.getParcelable<RecipesHuman>(ARG_RECIPES)!!

        adapter.addAll(recipes.ingradients)
        with(rvIngradients) {
            adapter = this@RecipesDetailFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        adapterSteps.addAll(recipes.steps)
        with(rvSteps) {
            adapter = this@RecipesDetailFragment.adapterSteps
            layoutManager = LinearLayoutManager(context)
        }

        tvName.text = recipes.name
        tvLikeCount.text = recipes.like.toString()
        tvUser.text = "@${recipes.user.name}"
        tvDescription.text = recipes.description
        tvRecipes.text = recipes.recipes

        btnLike.setOnClickListener {
            presenter.like(recipes.id)
        }

        btnShare.setOnClickListener() {
            presenter.share(recipes.link)
        }

        btnUserList.setOnClickListener {
            presenter.openListByUser(recipes.user)
        }
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

        fun create(
                recipes: RecipesHuman
        ): RecipesDetailFragment {
            val fragment = RecipesDetailFragment()

            val args = Bundle()
            args.putParcelable(ARG_RECIPES, recipes)
            fragment.arguments = args

            return fragment
        }
    }
}