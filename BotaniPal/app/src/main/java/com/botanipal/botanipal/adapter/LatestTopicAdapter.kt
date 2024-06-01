package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.Commodity
import com.botanipal.botanipal.data.Topics
import com.botanipal.botanipal.databinding.CommodityItemBinding
import com.botanipal.botanipal.databinding.TopicItemBinding
import com.botanipal.botanipal.helper.CommodityDiffCallback
import com.botanipal.botanipal.helper.TopicsDiffCallback

class LatestTopicAdapter(private var topics: List<Topics>) : RecyclerView.Adapter<LatestTopicAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: TopicItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topic: Topics) {
            binding.topicTitle.text = topic.title
            binding.topicDesc.text = topic.description
        }
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val topic = topics[position]
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

    override fun getItemCount(): Int = topics.size

    fun updateCommodities(newTopics: List<Topics>) {
        val topicDiffCallback = TopicsDiffCallback(this.topics, newTopics)
        val diffResult = DiffUtil.calculateDiff(topicDiffCallback)
        this.topics = newTopics
        diffResult.dispatchUpdatesTo(this)
    }
}