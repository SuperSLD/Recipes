package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseHolderFactory
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.IngradientHuman

class SelectComponentHolderFactory: BaseHolderFactory() {
    companion object {
        const val TITLE_HOLDER = 0
        const val SELECT_COMPONENT_HOLDER = 1
        const val EMPTY_HOLDER = 2

        const val SELECTED_LIST_UPDATE_EVENT = 0
    }

    /**
     * Создаем холжер для каждого элемента.
     *
     * @param type тип элемента из констант указанных выше.
     * @param parent
     * @param data
     */
    override fun create(type: Int, parent: ViewGroup, data: Any?): BaseViewHolder? {
        return when (type) {
            TITLE_HOLDER -> return SelectComponentTitleHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_select_component_header, parent, false)
            )
            EMPTY_HOLDER -> return SelectComponentEmptyHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_select_component_empty, parent, false)
            )
            SELECT_COMPONENT_HOLDER -> return IngradientHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_select_ingradient, parent, false),
                    data as MutableList<IngradientHuman>,
                    this
            )
            else -> null
        }
    }
}