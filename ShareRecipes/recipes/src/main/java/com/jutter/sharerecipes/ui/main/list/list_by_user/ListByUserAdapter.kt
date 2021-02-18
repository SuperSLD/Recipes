package com.jutter.sharerecipes.ui.main.list.list_by_user

import com.jutter.difcycler.DifAdapter
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderFactory
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.UserHuman
import com.jutter.sharerecipes.ui.main.list.list_by_user.holders.ItemHolder
import com.jutter.sharerecipes.ui.main.list.list_by_user.holders.ItemProfileHolder

class ListByUserAdapter (
        private var selectRecipes: (RecipesHuman) -> Unit
) : DifAdapter() {

    companion object {
        const val PROFILE_HOLDER = 0
        const val ITEM_HOLDER = 1

        const val SELECT_RECIPES_EVENT = 0
    }

    private val list = mutableListOf<RecipesHuman>()

    override fun initFactory(): HolderFactory {
        return HolderFactory(hashMapOf(
            PROFILE_HOLDER to ItemProfileHolder::class.java,
            ITEM_HOLDER to ItemHolder::class.java
        )
        )
        .onEvent { id, data ->
            when(id) {
                SELECT_RECIPES_EVENT -> selectRecipes(data as RecipesHuman)
            }
        }
    }

    private var user: UserHuman? = null

    override fun onBindViewHolder(holderDF: DFBaseHolder, position: Int) {
        holderDF.bind(if (position == 0) user else list[position - 1])
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }

    override fun getItemCount(): Int {
       return list.size + 1
    }

    fun addAll(list: MutableList<RecipesHuman>, userHuman: UserHuman) {
        this.list.clear()
        this.list.addAll(list)
        this.user = userHuman
        notifyDataSetChanged()
    }
}

