package com.jutter.sharerecipes.models.human

data class ListByUserHuman(
        var user: UserHuman,
        var recipes: MutableList<RecipesHuman>
)