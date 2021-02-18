package com.jutter.sharerecipes.ui.main.list.user_recipes.create_recipes

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.StepHuman
import kotlinx.android.synthetic.main.item_create_step.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.btnDelete
import java.util.*

class CreateRecipesStepAdapter: RecyclerView.Adapter<BaseViewHolder>(), ItemTouchHelperAdapter {

    private val list = mutableListOf<StepHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_create_step, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        (holder as ItemHolder).myCustomEditTextListener.updatePosition(position)
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

    fun addStep() {
        this.list.add(
                StepHuman(
                        number = this.list.size + 1,
                        text = ""
                )
        )
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        private val animationDuration = 200L
        var myCustomEditTextListener = MyCustomEditTextListener()

        init {
            itemView.etStep.addTextChangedListener(myCustomEditTextListener)
        }

        override fun bind (data: Any?) {
            val step = data as StepHuman
            with(itemView) {
                alpha = 1f

                etStep.setText(step.text)
                tvNumber.text = step.number.toString()

                btnDelete.setOnClickListener {
                    itemView.animate().alpha(0f).setDuration(animationDuration).start()
                    itemView.postDelayed({
                        list.remove(step)
                        fixNumbers()
                        notifyDataSetChanged()
                    }, animationDuration)
                }
            }
        }
    }

    fun fixNumbers() {
        for (position in 0 until list.size) {
            list[position].number = position + 1
        }
    }

    override fun onItemDismiss(position: Int) {

    }

    fun getStepList(): MutableList<StepHuman> {
        return this.list
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(list, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(list, i, i - 1)
            }
        }
        fixNumbers()
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    inner class MyCustomEditTextListener : TextWatcher {
        private var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            // no op
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {
            list[position].text = charSequence.toString()
        }

        override fun afterTextChanged(editable: Editable) {
            // no op
        }
    }
}

