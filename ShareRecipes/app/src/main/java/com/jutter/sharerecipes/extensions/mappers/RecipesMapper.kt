package com.jutter.sharerecipes.extensions.mappers

import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.human.RecipesHuman
import com.jutter.sharerecipes.models.human.RecommendationHuman
import com.jutter.sharerecipes.models.server.IngradientResponse
import com.jutter.sharerecipes.models.server.RecipesResponse
import com.jutter.sharerecipes.models.server.RecommendationResponse

fun RecipesResponse.toRecipesHuman(): RecipesHuman {
    return RecipesHuman(
            id = id,
            name = name,
            description = description,
            recipes = recipes,
            ingradients = ingradients.toIngradientHumanList(),
            user = user.toUserHuman(),
            like = like,
            steps = steps.toStepHumanList(),
            link = link
    )
}

fun MutableList<RecipesResponse>.toRecipesHumanList(): MutableList<RecipesHuman> {
    val result = mutableListOf<RecipesHuman>()
    forEach {
        result.add(it.toRecipesHuman())
    }
    return result
}

fun RecommendationResponse.toRecommendationHuman(): RecommendationHuman {
    return RecommendationHuman(
        popular = popular.toRecipesHumanList(),
        recommendation = recommendation.toRecipesHumanList()
    )
}