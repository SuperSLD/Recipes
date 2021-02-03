package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes.select_components.holders

import android.annotation.SuppressLint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.IngradientHuman
import kotlinx.android.synthetic.main.item_select_ingradient.view.*
import kotlinx.android.synthetic.main.item_tape_header.view.*
import timber.log.Timber

class IngradientHolder(
        itemView: View,
        var selectedList: MutableList<IngradientHuman>,
        var holderFactory: SelectComponentHolderFactory
) : BaseViewHolder(itemView) {

    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        val ingradient = data as IngradientHuman

        var checked = selectedList.contains(ingradient)

        with(itemView) {
            tvName.text = ingradient.name
            checkbox.isChecked = checked
            itemView.setOnClickListener {
                if (selectedList.contains(ingradient)) {
                    selectedList.remove(ingradient)
                } else {
                    selectedList.add(ingradient)
                }
                holderFactory.makeEvent(SelectComponentHolderFactory.SELECTED_LIST_UPDATE_EVENT)
                checked = !checked
                checkbox.isChecked = checked
            }
        }
    }
}