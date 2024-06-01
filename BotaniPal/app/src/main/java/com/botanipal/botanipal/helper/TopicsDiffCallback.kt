package com.botanipal.botanipal.helper

import androidx.recyclerview.widget.DiffUtil
import com.botanipal.botanipal.data.Commodity
import com.botanipal.botanipal.data.Topics

class TopicsDiffCallback(private val oldList: List<Topics>, private val newList: List<Topics>) : DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}