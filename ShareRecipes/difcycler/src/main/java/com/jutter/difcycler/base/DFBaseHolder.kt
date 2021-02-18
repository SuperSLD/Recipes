package com.jutter.difcycler.base

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Абстракный холдер.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
abstract class DFBaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var event = { _:Int, _:Any?->}

    /**
     * Связка данных с ходером.
     * Каждая реализация самостоятельно решает какой
     * тип данный в итоге она обрабатывает.
     *
     * @param data какой-то объект
     */
    abstract fun bind(data: Any?)

    /**
     * Вызов события для передачи данных в адаптер.
     *
     * @param id идентификатор события указанный в адаптере.
     * @param data какой-то объект
     */
    protected fun makeEvent(id: Int, data: Any?) {
        event(id, data)
    }
    protected fun makeEvent(id: Int) {
        event(id, null)
    }

    /**
     * Подключения калбека из фабрики.
     *
     * @param event калбек из фабрики.
     */
    fun onEvent(event: (Int, Any?) -> Unit) {
        this.event = event
    }
}