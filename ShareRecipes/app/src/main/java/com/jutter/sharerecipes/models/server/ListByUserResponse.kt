package com.jutter.sharerecipes.models.server

data class ListByUserResponse(
        var user: UserResponse,
        var recipes: MutableList<RecipesResponse>
)