package com.botanipal.botanipal.ui.price

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
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

        observeViewModel()
//        getCommodities()
//        binding?.bawangCardView?.visibility = View.VISIBLE
//        binding?.cabeCardView?.visibility = View.VISIBLE
//        binding?.jagungCardView?.visibility = View.VISIBLE
//        binding?.kacangCardView?.visibility = View.VISIBLE
//        binding?.kedelaiCardView?.visibility = View.VISIBLE
//        binding?.kentangCardView?.visibility = View.VISIBLE
//        binding?.kolCardView?.visibility = View.VISIBLE
//        binding?.tomatCardView?.visibility = View.VISIBLE


//        commodityAdapter = PriceAdapter(getCommodities())


//        viewModel.listCommodity.observe(viewLifecycleOwner) {
//            progressBar.visibility = View.GONE
//            commodityAdapter.updateCommodity(getCommodities())
//
////            commodityAdapter = PriceAdapter(getCommodities())
//        }

        viewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        if (viewModel.listCommodity.value.isNullOrEmpty()) {
            observeViewModel()
            getCommodities()
        }

//            binding?.tvBawangPrice?.text = viewModel.bawangPrice.value.toString()
//            binding?.tvCabePrice?.text = viewModel.cabePrice.value.toString()
//            binding?.tvJagungPrice?.text = viewModel.jagungPrice.value.toString()
//            binding?.tvKacangPrice?.text = viewModel.kacangPrice.value.toString()
//            binding?.tvKedelaiPrice?.text = viewModel.kedelaiPrice.value.toString()
//            binding?.tvKentangPrice?.text = viewModel.kentangPrice.value.toString()
//            binding?.tvKolPrice?.text = viewModel.kolPrice.value.toString()
//            binding?.tvTomatPrice?.text = viewModel.tomatPrice.value.toString()
//
//            binding?.bawangCardView?.visibility = View.VISIBLE
//            binding?.cabeCardView?.visibility = View.VISIBLE
//            binding?.jagungCardView?.visibility = View.VISIBLE
//            binding?.kacangCardView?.visibility = View.VISIBLE
//            binding?.kedelaiCardView?.visibility = View.VISIBLE
//            binding?.kentangCardView?.visibility = View.VISIBLE
//            binding?.kolCardView?.visibility = View.VISIBLE
//            binding?.tomatCardView?.visibility = View.VISIBLE



//        }

//        viewModel.isSuccessful.observe(viewLifecycleOwner) {
//            binding?.bawangCardView?.visibility = View.VISIBLE
//            val bawang = viewModel.bawangPrice.value.toString()
//            if (bawang != null) {
//                binding?.tvBawangPrice?.text = bawang
//            } else {
//                binding?.tvBawangPrice?.text = "0"
//            }
////            binding?.tvBawangPrice?.text = viewModel.bawangPrice.value.toString()
//            progressBar.visibility = View.GONE
//
//        }


    }

    private fun observeViewModel() {
//        try {
//            with(viewModel) {
//                getBawang()
//                getCabe()
//                getJagung()
//                getKacang()
//                getKedelai()
//                getKentang()
//                getKol()
//                getTomat()
//                getListPrice()

                viewModel.listCommodity.observe(viewLifecycleOwner) {commodity ->
                    progressBar.visibility = View.GONE
                    commodityAdapter.updateCommodity(commodity)
                    Log.d("PriceFragment", "observeViewModel: $commodity")
                }

                viewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
                    progressBar.visibility = if (it) View.VISIBLE else View.GONE
                }

        viewModel.getListPrice()


//            } catch (e: Exception) {
//                Log.e("PriceFragment", "observeViewModel: ${e.message}", e)
//
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            Log.e("PriceFragment", "observeViewModel: $errorBody")
//        }
//        Log.d("PriceFragment", "observeViewModel: ${viewModel.bawangPrice.value}")
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