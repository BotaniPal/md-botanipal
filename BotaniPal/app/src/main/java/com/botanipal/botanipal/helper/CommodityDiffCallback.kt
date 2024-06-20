package com.botanipal.botanipal.helper

import androidx.recyclerview.widget.DiffUtil
import com.botanipal.botanipal.data.model.Commodity

class CommodityDiffCallback(private val oldList: List<Commodity?>, private val newList: List<Commodity?>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.name == newList[newItemPosition]?.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}