package com.jutter.sharerecipes.extensions.mappers

import com.jutter.sharerecipes.models.human.StepHuman
import com.jutter.sharerecipes.models.server.StepRequest
import com.jutter.sharerecipes.models.server.StepResponse

fun StepResponse.toStepHuman(): StepHuman {
    return StepHuman(
            number = number,
            text = text
    )
}

fun MutableList<StepResponse>.toStepHumanList(): MutableList<StepHuman> {
    val result = mutableListOf<StepHuman>()
    forEach{
        result.add(it.toStepHuman())
    }
    return result
}

fun StepHuman.toStepRequest(): StepRequest {
    return StepRequest(
            number = number,
            text = text
    )
}

fun MutableList<StepHuman>.toStepRequestList(): MutableList<StepRequest> {
    val result = mutableListOf<StepRequest>()
    forEach{
        result.add(it.toStepRequest())
    }
    return result
}