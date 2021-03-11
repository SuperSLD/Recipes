package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.IngradientHuman
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName

class CreateRecipesComponentAdapter(
        private val deleteComponent: (IngradientHuman) -> Unit?
): RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<IngradientHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_create_ingradient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun addAll(list: MutableList<IngradientHuman>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        private val animationDuration = 200L

        override fun bind (data: Any?) {
            val ingradientHuman = data as IngradientHuman
            with(itemView) {
                alpha = 1f
                tvName.text = ingradientHuman.name
                btnDelete.setOnClickListener {
                    itemView.animate().alpha(0f).setDuration(animationDuration).start()
                    itemView.postDelayed({
                        notifyDataSetChanged()
                        list.remove(ingradientHuman)
                        deleteComponent(ingradientHuman)
                    }, animationDuration)
                }
            }
        }
    }

}

