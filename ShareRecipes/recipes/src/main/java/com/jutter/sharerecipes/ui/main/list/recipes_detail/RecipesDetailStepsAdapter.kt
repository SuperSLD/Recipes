package com.jutter.sharerecipes.ui.main.list.recipes_detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.StepHuman
import kotlinx.android.synthetic.main.item_detail_step.view.*

class RecipesDetailStepsAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<StepHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_step, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun addAll(list: MutableList<StepHuman>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        override fun bind (data: Any?) {
            val step = data as StepHuman
            with(itemView) {
                tvNumber.text = step.number.toString()
                tvText.text = step.text
            }
        }
    }

}

