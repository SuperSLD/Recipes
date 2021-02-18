package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.CategoryHuman
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.ui.main.list.holders.TapeHolderFactory
import com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.holders.SelectComponentHolderFactory

class SelectComponentsAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    private val holderFactory = SelectComponentHolderFactory()
    /**
     * Пара (INT, ANY) -> (Тип для фабрики, Данные которые обрабатывает холдер данного типа)
     */
    private val list = mutableListOf<Pair<Int, Any?>>()

    private val selectedList = mutableListOf<IngradientHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return holderFactory.create(viewType, parent, selectedList)!!
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position].second)
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return list[position].first
    }

    fun setSelected(selected: MutableList<IngradientHuman>) {
        this.selectedList.clear()
        this.selectedList.addAll(selected)
    }

    fun addData(list: MutableList<CategoryHuman>) {
        this.list.clear()

        if (list.size != 0) {
            for (category in list) {
                this.list.add(Pair(SelectComponentHolderFactory.TITLE_HOLDER, category.name))
                for (ingradient in category.ingradients) {
                    this.list.add(Pair(SelectComponentHolderFactory.SELECT_COMPONENT_HOLDER, ingradient))
                }
            }
        } else {
            this.list.add(Pair(SelectComponentHolderFactory.EMPTY_HOLDER, null))
        }
        notifyDataSetChanged()
    }

    fun deleteItem(ingradientHuman: IngradientHuman) {
        selectedList.remove(ingradientHuman)
        for (position in this.list.indices) {
            if (ingradientHuman == this.list[position].second) {
                notifyItemChanged(position)
            }
        }
    }

    fun getSelected(): MutableList<IngradientHuman> = selectedList

    fun clickEvent(event: (MutableList<IngradientHuman>) -> Unit) {
        holderFactory.setClickEvent {id, _ ->
            if (id == SelectComponentHolderFactory.SELECTED_LIST_UPDATE_EVENT) {
                event(selectedList)
            }
        }
    }
}