package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.model.ChaTanis
import com.botanipal.botanipal.databinding.TopicItemBinding
import com.botanipal.botanipal.helper.ChaTanisDiffCallback

class ChatanisAdapter (private var chaTanis: List<ChaTanis>) : RecyclerView.Adapter<ChatanisAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: TopicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chaTanis: ChaTanis) {
            binding.topicTitle.text = chaTanis.title
            binding.topicDesc.text = chaTanis.description
        }
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val topic = chaTanis[position]
        holder.bind(topic)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = TopicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = chaTanis.size

    fun updateForum(newChaTanis: List<ChaTanis>) {
        val chaTanisDiffCallback = ChaTanisDiffCallback(this.chaTanis, newChaTanis)
        val diffResult = DiffUtil.calculateDiff(chaTanisDiffCallback)
        this.chaTanis = newChaTanis
        diffResult.dispatchUpdatesTo(this)
    }
}