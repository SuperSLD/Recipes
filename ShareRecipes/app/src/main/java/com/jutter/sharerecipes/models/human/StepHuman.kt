package com.jutter.sharerecipes.models.human

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StepHuman(
        var number: Int,
        var text: String
) : Parcelable