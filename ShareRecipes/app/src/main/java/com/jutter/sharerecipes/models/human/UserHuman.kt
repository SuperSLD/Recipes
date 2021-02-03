package com.jutter.sharerecipes.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserHuman (
        var id: Int,
        var name: String,
        var image: String
): Parcelable