package com.torrydo.transe.listAdapter.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class GenericAdapter<T>(listItems: List<T>?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItems: List<T>

    init {
        this.listItems = listItems ?: emptyList()
    }

    fun setItems(listItems: List<T>) {
        this.listItems = listItems
        notifyDataSetChanged()
    }
    fun getCurrentItems() = listItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(listItems[position], position)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutId(position, listItems[position])
    }

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

//    internal interface ViewHolderConfigurator<T>{
//        fun onConfigViewHolder(parent: ViewGroup, viewType: Int, item: T)
//    }

    internal interface Binder<T> {
        fun bind(item: T, position: Int)
    }

}
