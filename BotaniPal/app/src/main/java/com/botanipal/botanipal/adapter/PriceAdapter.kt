package com.botanipal.botanipal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.data.model.Commodity
import com.botanipal.botanipal.databinding.PriceItemBinding
import com.botanipal.botanipal.helper.CommodityDiffCallback

class PriceAdapter(private var prices: List<Commodity>) : RecyclerView.Adapter<PriceAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: PriceItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(commodity: Commodity) {
            binding.ivCommodityPhoto.setImageResource(commodity.image)
            binding.tvCommodityName.text = commodity.name
            binding.tvCommodityPrice.text = commodity.price
        }
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        val commodity = prices[position]
        holder.bind(commodity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = PriceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = prices.size

    fun updatePrices(newPrices: List<Commodity>) {
        val commodityDiffCallback = CommodityDiffCallback(this.prices, newPrices)
        val diffResult = DiffUtil.calculateDiff(commodityDiffCallback)
        this.prices = newPrices
        diffResult.dispatchUpdatesTo(this)
    }
}