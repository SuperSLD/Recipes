package com.jutter.difcycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jutter.difcycler.base.DFBaseHolder
import com.jutter.difcycler.base.HolderFactory

/**
 * Адаптер для разных элементов.
 *
 * @author Leonid Solyanoy (solyanoy.leonid@gmail.com)
 */
abstract class DifAdapter: RecyclerView.Adapter<DFBaseHolder>() {

    private var holderFactory: HolderFactory? = null

    init {
        @Suppress("LeakingThis")
        holderFactory = initFactory()
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