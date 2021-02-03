package com.jutter.sharerecipes.extensions.mappers

import com.jutter.sharerecipes.models.human.UserHuman
import com.jutter.sharerecipes.models.server.UserResponse

fun UserResponse.toUserHuman(): UserHuman {
    return UserHuman(
            id = id,
            name = name,
            image = image
    )
}