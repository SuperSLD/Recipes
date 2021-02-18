package com.jutter.sharerecipes.models.server

data class RecipesResponse (
        var id: String,
        var name: String,
        var description: String,
        var ingradients: MutableList<IngradientResponse>,
        var recipes: String,
        var user: UserResponse,
        var steps: MutableList<StepResponse>,
        var link: String,

        var like: Int
)

data class CreateRecipesBody(
        var name: String,
        var description: String,
        var recipes: String,
        var ingradients: MutableList<IngradientResponse>,

        var steps: MutableList<StepRequest>
)

data class SearchRecipesByIngradientsBody(
        var ingradients: MutableList<IngradientResponse>
)

data class RecommendationResponse(
        var popular: MutableList<RecipesResponse>,
        var recommendation: MutableList<RecipesResponse>
)