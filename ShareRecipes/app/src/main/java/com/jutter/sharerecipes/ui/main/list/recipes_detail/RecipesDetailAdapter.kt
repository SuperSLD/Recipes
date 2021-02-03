package com.jutter.sharerecipes.ui.main.list.recipes_detail

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.common.base.BaseViewHolder
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class RecipesDetailAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<IngradientHuman>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_detail_ingradient, parent, false)
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
        override fun bind (data: Any?) {
            val ingradientHuman = data as IngradientHuman
            with(itemView) {
                tvName.text = ingradientHuman.name
            }
        }
    }

}

