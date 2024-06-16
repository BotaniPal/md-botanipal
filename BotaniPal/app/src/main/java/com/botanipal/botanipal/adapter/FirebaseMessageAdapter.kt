package com.botanipal.botanipal.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.R
import com.botanipal.botanipal.data.model.Message
import com.botanipal.botanipal.databinding.MessageItemBinding
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FirebaseMessageAdapter (
    options: FirebaseRecyclerOptions<Message>,
    private val currentUserName: String?
) : FirebaseRecyclerAdapter<Message, FirebaseMessageAdapter.MessageViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message_item, parent, false)
        val binding = MessageItemBinding.bind(view)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        holder.bind(model)
    }

    inner class MessageViewHolder(private val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Message) {
            binding.tvMessage.text = item.text
            setTextColor(item.name, binding.tvMessage)
            binding.tvMessenger.text = item.name
            Glide.with(itemView.context)
                .load(item.photoUrl)
                .circleCrop()
                .into(binding.ivMessenger)
            if (item.timestamp != null) {
                binding.tvTimestamp.text = DateUtils.getRelativeTimeSpanString(item.timestamp)
            }
        }

        private fun setTextColor(userName: String?, textView: TextView) {
            if (currentUserName == userName && userName != null) {
                textView.setBackgroundResource(R.drawable.rounded_message_primary)
            } else {
                textView.setBackgroundResource(R.drawable.rounded_message_secondary)
            }
        }
    }
}