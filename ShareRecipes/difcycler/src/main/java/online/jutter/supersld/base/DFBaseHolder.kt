package online.jutter.supersld.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView

/**
 * Абстракный холдер.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
abstract class DFBaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var event = { _:Int, _:Any?->}

    private var mPosition = 0
    private var mListSize = 0

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
    @CallSuper
    protected fun makeEvent(id: Int, data: Any?) {
        event(id, data)
    }
    @CallSuper
    protected fun makeEvent(id: Int) {
        event(id, null)
    }

    /**
     * Подключения калбека из фабрики.
     *
     * @param event калбек из фабрики.
     */
    @CallSuper
    fun onEvent(event: (Int, Any?) -> Unit) {
        this.event = event
    }

    /**
     * Передача длинны списка и позиции.
     * @param size длинна списка.
     * @param position индекс забинженого элемента.
     */
    fun setSizeAndPosition(size: Int, position: Int) {
        this.mPosition = position
        this.mListSize = size
    }

    /**  Получение позиции в списке  */
    fun getPositionInList() = mPosition
    /**  Получение длинны списка  */
    fun getListSize() = mListSize
}