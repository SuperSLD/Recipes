package com.jutter.sharerecipes.models.server

data class CategoryResponse(
        var name: String,
        var ingradients: MutableList<IngradientResponse>
)

data class IngradientResponse (
        var id: Int,
        var name: String
)