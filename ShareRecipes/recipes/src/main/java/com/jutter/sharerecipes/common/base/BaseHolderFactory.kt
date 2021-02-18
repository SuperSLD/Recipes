package com.jutter.sharerecipes.common.base

import android.view.ViewGroup

open class BaseHolderFactory  {
    private var clickEvent = {_:Int, _:Any? -> }

    fun setClickEvent(event: (Int, Any?) -> Unit) {
        clickEvent = event
    }

    fun makeEvent(eventId: Int) {
        clickEvent(eventId, null)
    }

    fun makeEvent(eventId: Int, data: Any?) {
        clickEvent(eventId, data)
    }

    open fun create(type: Int, parent: ViewGroup, data: Any?): BaseViewHolder? {
        return null
    }
}