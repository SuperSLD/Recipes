package com.jutter.sharerecipes.models.server

data class UserResponse (
        var id: Int,
        var name: String,
        var image: String,
        var link: String
)

data class UserUpdateBody(
        var image: String
)