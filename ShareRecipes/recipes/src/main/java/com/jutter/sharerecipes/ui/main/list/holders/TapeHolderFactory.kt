package com.jutter.sharerecipes.ui.main.list.holders

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseHolderFactory
import com.jutter.sharerecipes.common.base.BaseViewHolder

class TapeHolderFactory: BaseHolderFactory() {
    companion object {
        const val HEADER_HOLDER = 0
        const val USER_RECIPES_HOLDER = 4
        const val TOP_HOLDER = 2
        const val REC_HOLDER = 3
        const val SEARCH_BY_INGRADIENTS_HOLDER = 5

        const val USER_RECIPES_CLICK_EVENT = 0
        const val SEARCH_BY_INGRADIENTS_EVENT = 3
        const val CLICK_RECIPES_EVENT = 1
    }

    /*
    /**
     * Создаем холжер для каждого элемента.
     *
     * @param type тип элемента из констант указанных выше.
     * @param parent
     * @param data
     */
    override fun create(type: Int, parent: ViewGroup, data: Any?): BaseViewHolder? {
        return when (type) {
            HEADER_HOLDER -> return TapeTitleHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_tape_header, parent, false)
            )
            USER_RECIPES_HOLDER -> return UserRecipesHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_user_recipes, parent, false),
                    this
            )
            SEARCH_BY_INGRADIENTS_HOLDER -> return SearchByIngradientsHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_search_button, parent, false),
                this
            )
            REC_HOLDER -> TapeRecipesBigHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recipes_card, parent, false),
                this
            )
            TOP_HOLDER -> TapeRecipesScrollHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_horisontal_card_list, parent, false),
                this
            )
            else -> null
        }
    }

     */
}