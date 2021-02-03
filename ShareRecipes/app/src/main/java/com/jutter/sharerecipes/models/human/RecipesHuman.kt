package com.jutter.sharerecipes.models.human

import android.os.Parcelable
import com.jutter.sharerecipes.models.server.RecipesResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecipesHuman (
        var id: String,
        var name: String,
        var description: String,
        var ingradients: MutableList<IngradientHuman>,
        var recipes: String,
        var user: UserHuman,
        var steps: MutableList<StepHuman>,

        var like: Int
): Parcelable

data class RecommendationHuman(
        var popular: MutableList<RecipesHuman>,
        var recommendation: MutableList<RecipesHuman>
)