package online.jutter.supersld.base

import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import online.jutter.supersld.DifAdapter

/**
 * Абстракный холдер.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
abstract class DFBaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var event = { _:Int, _:Any?->}

    private var mAdapter: DifAdapter? = null

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

    /** Передача адаптера в холжер.
     *  Вызывается автоматически при создании элемента.
     *
     *  @param diffAdapter родительский адаптер
     */
    fun setAdapter(diffAdapter: DifAdapter) {
        this.mAdapter = diffAdapter
    }
    /** получение адаптера */
    fun getAdapter() = mAdapter
}