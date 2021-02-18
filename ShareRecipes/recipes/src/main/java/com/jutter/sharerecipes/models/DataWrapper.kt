package com.jutter.sharerecipes.models

data class DataWrapper<T>(
    val success: Boolean?,
    val message: String?,
    val data: T?
)