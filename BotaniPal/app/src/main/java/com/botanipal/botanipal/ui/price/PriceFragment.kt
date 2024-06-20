package com.botanipal.botanipal.ui.price

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.PriceAdapter
import com.botanipal.botanipal.data.model.Commodity
import com.botanipal.botanipal.data.response.DataPrice
import com.botanipal.botanipal.databinding.FragmentPriceBinding
import com.botanipal.botanipal.ui.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.HttpException

class PriceFragment : Fragment() {
    private var _binding: FragmentPriceBinding? = null
    private val binding get() = _binding
    private lateinit var progressBar: ProgressBar
    private lateinit var commodityAdapter: PriceAdapter
    private val viewModel: PriceViewModel by viewModels() {
        ViewModelFactory.getInstance(requireContext())
    }

    companion object {
        fun newInstance() = PriceFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding?.fragmentProgressBar ?: view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        commodityAdapter = PriceAdapter(emptyList())

        val recyclerView: RecyclerView = view.findViewById(R.id.priceRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = commodityAdapter

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        recyclerView.addItemDecoration(dividerItemDecoration)

        observeViewModel()


        viewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        if (viewModel.listCommodity.value.isNullOrEmpty()) {
            observeViewModel()
            getCommodities()
        }

    }

    private fun observeViewModel() {
                viewModel.listCommodity.observe(viewLifecycleOwner) {commodity ->
                    progressBar.visibility = View.GONE
                    commodityAdapter.updateCommodity(commodity)
                    Log.d("PriceFragment", "observeViewModel: $commodity")
                }

                viewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
                    progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }

        viewModel.getListPrice()

    }

    private fun getCommodities(): List<Commodity> {
        val commodityPhoto = resources.obtainTypedArray(R.array.commodity_photos)
        val commodityNames = resources.getStringArray(R.array.commodity_name)

        val listCommodityResult = ArrayList<Commodity>()
        for (position in commodityNames.indices) {
            val commodityName = commodityNames[position]
            val commodityPrice = when (commodityName) {
                "Bawang Merah" -> viewModel.bawangPrice.value ?: 0
                "Cabe Rawit Merah" -> viewModel.cabePrice.value ?: 0
                "Jagung" -> viewModel.jagungPrice.value?: 0
                "Kacang Tanah" -> viewModel.kacangPrice.value?: 0
                "Kedelai" -> viewModel.kedelaiPrice.value?: 0
                "Kentang" -> viewModel.kentangPrice.value?: 0
                "Kol" -> viewModel.kolPrice.value?: 0
                "Tomat" -> viewModel.tomatPrice.value?: 0
                else -> 0
            }
            listCommodityResult.add( Commodity(
                commodityPhoto.getResourceId(position, -1),
                commodityName,
                commodityPrice
            ))
        }
        commodityPhoto.recycle()

        return listCommodityResult
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_price, container, false)
    }
}