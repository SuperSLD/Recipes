package com.jutter.sharerecipes.ui.main.list.list_by_user

import android.annotation.SuppressLint
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
import com.jutter.sharerecipes.models.human.UserHuman
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_recipes_card.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.*
import kotlinx.android.synthetic.main.item_select_ingradient_header.view.tvName
import kotlinx.android.synthetic.main.item_user_info.view.*
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class ListByUserAdapter (
        private var selectRecipes: (RecipesHuman) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val list = mutableListOf<RecipesHuman>()
    private var user: UserHuman? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 1) ItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipes_card, parent, false)
        ) else  ItemProfileHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_info, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(if (position == 0) user else list[position - 1])
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

    inner class ItemHolder(itemView: View): BaseViewHolder(itemView) {
        override fun bind (data: Any?) {
            val recipes = data as RecipesHuman
            with(itemView) {
                tvName.text = recipes.name
                tvDescription.text = recipes.description
                tvUser.text = "@${recipes.user.name}"
                tvLikeCount.text = recipes.like.toString()

                setOnClickListener {
                    selectRecipes(recipes)
                }
            }
        }
    }

    inner class ItemProfileHolder(itemView: View): BaseViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        override fun bind (data: Any?) {
            val user = data as UserHuman
            with(itemView) {
                tvName.text = "@${user.name}"

                Glide
                    .with(this)
                    .load(user.image)
                    .into(ivAvatar)
            }
        }
    }
}

