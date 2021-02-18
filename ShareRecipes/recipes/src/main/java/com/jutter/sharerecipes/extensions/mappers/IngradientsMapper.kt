package com.jutter.sharerecipes.extensions.mappers

import com.jutter.sharerecipes.models.human.CategoryHuman
import com.jutter.sharerecipes.models.human.IngradientHuman
import com.jutter.sharerecipes.models.server.CategoryResponse
import com.jutter.sharerecipes.models.server.IngradientResponse

fun IngradientResponse.toIngradientHuman(): IngradientHuman {
    return IngradientHuman(
            id = id,
            name = name
    )
}

fun MutableList<IngradientResponse>.toIngradientHumanList(): MutableList<IngradientHuman> {
    val result = mutableListOf<IngradientHuman>()
    forEach{
        result.add(it.toIngradientHuman())
    }
    return result
}

fun CategoryResponse.toCategoryHuman(): CategoryHuman {
    return CategoryHuman(
            name = name,
            ingradients = ingradients.toIngradientHumanList()
    )
}

fun MutableList<CategoryResponse>.toCategoryHumanList(): MutableList<CategoryHuman> {
    val result = mutableListOf<CategoryHuman>()
    forEach{
        result.add(it.toCategoryHuman())
    }
    return result
}

fun IngradientHuman.toIngradientResponse(): IngradientResponse {
    return IngradientResponse(
            id = id,
            name = name
    )
}

fun MutableList<IngradientHuman>.toIngradientResponseList(): MutableList<IngradientResponse> {
    val result = mutableListOf<IngradientResponse>()
    forEach {
        result.add(it.toIngradientResponse())
    }
    return result
}