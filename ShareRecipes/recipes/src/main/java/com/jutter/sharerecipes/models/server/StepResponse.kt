package com.jutter.sharerecipes.models.server

data class StepResponse(
        var text: String,
        var number: Int
)

data class StepRequest(
        var text: String,
        var number: Int
)