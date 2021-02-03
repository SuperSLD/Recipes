package com.jutter.sharerecipes.models.server

data class LoginBody (
    var login: String,
    var password: String
)

data class LoginResponse (
        var token: String
)