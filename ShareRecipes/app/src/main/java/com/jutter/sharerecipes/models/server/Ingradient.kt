package com.jutter.sharerecipes.models.server

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CategoryResponse(
        var name: String,
        var ingradients: MutableList<IngradientResponse>
)

data class IngradientResponse (
        var id: Int,
        var name: String
)