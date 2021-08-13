package online.jutter.supersld

import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import online.jutter.supersld.base.DFBaseHolder
import online.jutter.supersld.base.HolderFactory

/**
 * Адаптер для создания списков
 * из разных элементов.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
abstract class DifAdapter: RecyclerView.Adapter<DFBaseHolder>() {

    private var holderFactory: HolderFactory? = null

    init {
        @Suppress("LeakingThis")
        holderFactory = initFactory()
        holderFactory?.setAdapter(this)
    }

    /**
     * Инициализация фабрики.
     */
    protected abstract fun initFactory(): HolderFactory

    @Suppress
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DFBaseHolder {
        return holderFactory?.create(viewType, parent)!!
    }
}