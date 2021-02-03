package com.jutter.sharerecipes.ui.main.list.search_by_ingradients

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
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class HeaderSearchByIngradientsAdapter (
    private val editSelected: () -> Unit,
    private val removeIngradient: (IngradientHuman) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<IngradientHuman>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 0) ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_select_ingradient_header, parent, false)
        ) else EndItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_select_ingradient_header_add, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(if (position in list.indices) list[position] else null)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) 1 else 0
    }

    override fun getItemCount(): Int {
       return list.size + 1
    }

    fun addAll(list: MutableList<IngradientHuman>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        override fun bind (data: Any?) {
            val ingradient = data as IngradientHuman
            with(itemView) {
                tvName.text = ingradient.name
                btnDelete.setOnClickListener {
                    removeIngradient(ingradient)
                }
            }
        }
    }

    inner class EndItemHolder(itemView: View): BaseViewHolder(itemView) {
        override fun bind (data: Any?) {
            with(itemView) {
                btnDelete.setOnClickListener {
                    editSelected()
                }
            }
        }
    }
}

