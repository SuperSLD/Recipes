package com.jutter.sharerecipes.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CategoryHuman(
        var name: String,
        var ingradients: MutableList<IngradientHuman>
)

@Parcelize
data class IngradientHuman(
        var id: Int,
        var name: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return if (other is IngradientHuman) this.id == other.id else false
    }

}