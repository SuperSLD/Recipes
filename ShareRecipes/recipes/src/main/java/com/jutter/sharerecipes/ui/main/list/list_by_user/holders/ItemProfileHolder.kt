package com.jutter.sharerecipes.ui.main.list.list_by_user.holders

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderLayout
import com.jutter.sharerecipes.R
import com.jutter.sharerecipes.models.human.UserHuman
import kotlinx.android.synthetic.main.fragment_profile.view.*

@HolderLayout(layout = R.layout.item_user_info)
class ItemProfileHolder(itemView: View): DFBaseHolder(itemView) {
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