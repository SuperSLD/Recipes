package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseFragment
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.raspisanie.mai.extesions.addSystemBottomPadding
import kotlinx.android.synthetic.main.fragment_create_recipes.*
import kotlinx.android.synthetic.main.fragment_create_recipes.nested
import kotlinx.android.synthetic.main.fragment_user_recipes.include_toolbar
import kotlinx.android.synthetic.main.layout_loading_container.*
import kotlinx.android.synthetic.main.layout_toolbar_search.view.*


class CreateRecipesFragment : BaseFragment(R.layout.fragment_create_recipes), CreateRecipesView {

    @InjectPresenter
    lateinit var presenter: CreateRecipesPresenter

    private val adapter by lazy {
        CreateRecipesComponentAdapter(presenter::deleteComponent)
    }

    private val adapterStep by lazy {
        CreateRecipesStepAdapter()
    }

    private var ingradientsIsSelected = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setTittleToolBar(include_toolbar, R.string.create_recipes_title, R.drawable.ic_arrow_back, 0, 0)
        with(include_toolbar) {
            icClose.setOnClickListener { onBackPressed() }
        }

        nested.addSystemBottomPadding()

        btnSelectComponents.setOnClickListener {
            presenter.selectComponents()
        }

        btnCreate.isEnabled = false

        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { checkData() }
            override fun afterTextChanged(s: Editable?) {}
        }

        etName.addTextChangedListener(textWatcher)
        etDescription.addTextChangedListener(textWatcher)
        etRecipes.addTextChangedListener(textWatcher)

        btnCreate.setOnClickListener {
            presenter.createRecipes(
                    name = etName.text.toString(),
                    description = etDescription.text.toString(),
                    recipes = etRecipes.text.toString(),
                    steps = adapterStep.getStepList()
            )
        }

        with(rvComponents) {
            adapter = this@CreateRecipesFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        with(rvSteps) {
            adapter = this@CreateRecipesFragment.adapterStep
            layoutManager = LinearLayoutManager(context)
        }

        btnAddStep.setOnClickListener {
            adapterStep.addStep()
        }

        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapterStep)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rvSteps)
    }

    fun checkData() {
        btnCreate.isEnabled = !etName.text.isNullOrEmpty() &&
                !etDescription.text.isNullOrEmpty() &&
                !etRecipes.text.isNullOrEmpty() && ingradientsIsSelected
    }

    override fun onBackPressed() {
        presenter.back()
    }

    override fun selectIngradients(ingradients: MutableList<IngradientHuman>) {
        this.ingradientsIsSelected = ingradients.size > 0
        adapter.addAll(ingradients)
    }

    override fun toggleLoading(show: Boolean) {
        if (show) {
            loadingProgressBar.visibility = View.VISIBLE
            nested.visibility = View.GONE
        } else {
            loadingProgressBar.visibility = View.GONE
            nested.visibility = View.VISIBLE
        }
    }
}