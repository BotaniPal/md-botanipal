package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.model.Topics
import com.botanipal.botanipal.databinding.TopicItemBinding
import com.botanipal.botanipal.helper.TopicsDiffCallback

class ForumAdapter(private var topics: List<Topics>) : RecyclerView.Adapter<ForumAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    var onItemClick: ((String) -> Unit)? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(private val binding: TopicItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(topics[position].toString())
                }
            }
        }
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
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(topic)
        }
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

    fun updateForum(newTopics: List<Topics>) {
        val topicDiffCallback = TopicsDiffCallback(this.topics, newTopics)
        val diffResult = DiffUtil.calculateDiff(topicDiffCallback)
        this.topics = newTopics
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Topics)
    }
}