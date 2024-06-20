package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.model.Prediction
import com.botanipal.botanipal.data.model.Topics
import com.botanipal.botanipal.data.response.DataItem
import com.botanipal.botanipal.data.response.ScanData
import com.botanipal.botanipal.databinding.BookmarkPlantItemBinding
import com.botanipal.botanipal.helper.PlantDiffCallback

class PlantAdapter(private var prediction: List<DataItem>) : RecyclerView.Adapter<PlantAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var onItemClick: ((String) -> Unit)? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: BookmarkPlantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(prediction[position].toString())
                }
            }
        }

        fun bind(prediction: DataItem) {
            binding.tvBookmarkName.text = prediction.predictionResult
            binding.tvBookmarkType.text = prediction.predictionType
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val prediction = prediction[position]
        holder.bind(prediction)
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(prediction)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = BookmarkPlantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = prediction.size

    fun updatePlants(newPrediction: List<DataItem>) {
        val plantDiffCallback = PlantDiffCallback(this.prediction, newPrediction)
        val diffResult = DiffUtil.calculateDiff(plantDiffCallback)
        this.prediction = newPrediction
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataItem)
    }
}