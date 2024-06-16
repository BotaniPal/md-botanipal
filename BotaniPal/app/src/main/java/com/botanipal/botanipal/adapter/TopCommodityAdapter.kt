package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.Commodity
import com.botanipal.botanipal.databinding.CommodityItemBinding
import com.botanipal.botanipal.helper.CommodityDiffCallback

class TopCommodityAdapter(private var commodities: List<Commodity>) : RecyclerView.Adapter<TopCommodityAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: CommodityItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(commodity: Commodity) {
            binding.tvIconStart.setImageResource(commodity.image)
            binding.tvTitleComodity.text = commodity.name
            binding.tvPriceComodity.text = commodity.price
        }
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val commodity = commodities[position]
        holder.bind(commodity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = CommodityItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = commodities.size

    fun updateCommodities(newCommodities: List<Commodity>) {
        val commodityDiffCallback = CommodityDiffCallback(this.commodities, newCommodities)
        val diffResult = DiffUtil.calculateDiff(commodityDiffCallback)
        this.commodities = newCommodities
        diffResult.dispatchUpdatesTo(this)
    }
}