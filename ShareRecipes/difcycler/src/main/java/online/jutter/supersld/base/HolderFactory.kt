package online.jutter.supersld.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import online.jutter.supersld.exceptions.HolderCreateException
import online.jutter.supersld.exceptions.TypeNotFoundException
import java.lang.Exception

/**
 * Фабрика холдеров.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
class HolderFactory(
    private val typeMap: HashMap<Int, Class<*>>
) {

    private var event = {_:Int,_:Any?->}

    /**
     * Установка калбэка для собтытий холдера.
     *
     * @param event калбек.
     */
    @CallSuper
    fun onEvent(event: (Int, Any?) -> Unit): HolderFactory {
        this.event = event
        return this
    }

    /**
     * Создание элемента относительно его типа.
     *
     * @param type тип холдера.
     * @param parent view из адаптера.
     */
    fun create(type: Int, parent: ViewGroup): DFBaseHolder {
        var holderDF: DFBaseHolder? = null
        val clazz = typeMap[type] ?: throw TypeNotFoundException()
        var layout = -1
        for (annotation in clazz.annotations) {
            if (annotation is HolderLayout) {
                layout = annotation.layout
            }
        }
        if (layout < 0) throw HolderCreateException(
            "layout not found in ${clazz.name}, check @HolderLayout annotation"
        )
        try {
            holderDF = clazz.constructors[0].newInstance(
                LayoutInflater.from(parent.context).inflate(layout, parent, false)
            ) as DFBaseHolder
        } catch (ex: Exception) {
            throw HolderCreateException("can't create holder ${clazz.name} by default constructor")
        }
        holderDF.onEvent { id, data ->
            event(id, data)
        }
        return holderDF
    }
}