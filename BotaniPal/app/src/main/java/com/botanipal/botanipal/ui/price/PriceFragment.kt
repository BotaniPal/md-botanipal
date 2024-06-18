package com.botanipal.botanipal.ui.price

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.PriceAdapter
import com.botanipal.botanipal.databinding.FragmentPriceBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PriceFragment : Fragment() {
    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding
    private lateinit var progressBar: ProgressBar
    private lateinit var commodityAdapter: PriceAdapter
    private val viewModel: PriceViewModel by viewModels()

    companion object {
        fun newInstance() = PriceFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding?.fragmentProgressBar ?: view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        commodityAdapter = PriceAdapter(emptyList())

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_commodity)
        recyclerView.layoutManager = LinearLayoutManager(context)

        if (viewModel.commodity.value.isNullOrEmpty()) {
            viewModel.getCommodity()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_price, container, false)
    }
}