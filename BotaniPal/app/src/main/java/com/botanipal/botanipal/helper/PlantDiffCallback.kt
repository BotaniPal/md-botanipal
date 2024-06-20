package com.botanipal.botanipal.helper

import androidx.recyclerview.widget.DiffUtil
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.response.DataItem
import com.botanipal.botanipal.data.response.ScanData

class PlantDiffCallback(private val oldList: List<DataItem>, private val newList: List<DataItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].predictionResult == newList[newItemPosition].predictionResult
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}