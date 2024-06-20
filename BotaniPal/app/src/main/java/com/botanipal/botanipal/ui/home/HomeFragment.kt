package com.botanipal.botanipal.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.botanipal.botanipal.BuildConfig
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.ForumAdapter
import com.botanipal.botanipal.adapter.PriceAdapter
import com.botanipal.botanipal.adapter.TopCommodityAdapter
import com.botanipal.botanipal.data.model.Commodity
import com.botanipal.botanipal.data.model.Topics
import com.botanipal.botanipal.data.api.ApiConfig
import com.botanipal.botanipal.data.response.WeatherResponse
import com.botanipal.botanipal.databinding.FragmentHomeBinding
import com.botanipal.botanipal.ui.ViewModelFactory
import com.botanipal.botanipal.ui.price.PriceViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var commodityAdapter: TopCommodityAdapter
    private lateinit var topicAdapter: ForumAdapter
    private var lat: Float? = null
    private var lon: Float? = null
    private lateinit var locationString : String

    private val viewModel: HomeViewModel by lazy {
        ViewModelFactory.getInstance(requireActivity().application).create(HomeViewModel::class.java)
    }

    private val priceViewModel: PriceViewModel by lazy {
        ViewModelFactory.getInstance(requireActivity().application).create(PriceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSession().observe(viewLifecycleOwner) {
            binding.tvGreetingName.text = "${it.username}!"
        }


        commodityAdapter = TopCommodityAdapter(emptyList())
        topicAdapter = ForumAdapter(getTopics())
        retrieveLocation()

        val commodityRecyclerView = binding.rvComodity
        commodityRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        commodityRecyclerView.adapter = commodityAdapter

        val topicRecyclerView = binding.rvTopics
        topicRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        topicRecyclerView.adapter = topicAdapter

        val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
        topicRecyclerView.addItemDecoration(dividerItemDecoration)

        binding.btnPlantType.setOnClickListener {
            // Handle button click
        }
        binding.btnPlantDisease.setOnClickListener {
            // Handle button click
        }
        binding.btnCommodity.setOnClickListener {
//            Log.d("ButtonTest", "Button clicked")
//            findNavController().navigate(R.id.action_navigation_home_to_navigation_price)
        }
        binding.btnForum.setOnClickListener {
            // Handle button click
        }
        binding.btnChatanis.setOnClickListener {
            // Handle button click
        }
        binding.btnBookmark.setOnClickListener {
//            Log.d("ButtonTest", "Button clicked")
//            findNavController().navigate(R.id.navigation_bookmark)
        }

        observeViewModel()

        priceViewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
//            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        if (priceViewModel.listCommodity.value.isNullOrEmpty()) {
            observeViewModel()
            getCommodities()
        }

        return root
    }

    private fun retrieveLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        lat = location.latitude.toFloat()
                        lon = location.longitude.toFloat()
                        locationString = "$lat, $lon"

                        getWeatherData()

//                        Toast.makeText(requireContext(), "Location: $locationString", Toast.LENGTH_SHORT).show()
                        Log.d("Location", locationString)
                    } ?:
//                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                    Log.d("Location", "Location not found")
                }
                .addOnFailureListener { e ->
//                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("Location", "Error: ${e.message}")
                }
        } else {
            requestLocationPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Location permission granted.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Location permission denied.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getWeatherData() {
        viewModel.getWeather(locationString)
        viewModel.weatherData.observe(viewLifecycleOwner) {response ->
            val temp = "${response?.current?.temp_c}°C"
            Log.d("WeatherHome", "Response: $response")
            Log.d("WeatherHome", "Temperature: $temp")
            binding.tvDegrees.text = temp
            binding.tvWeather.text = response?.current?.condition?.text
            binding.tvLocation.text= response?.location?.name
            Glide.with(this@HomeFragment)
                .load("https:${response?.current?.condition?.icon}")
                .into(binding.ivWeather)

        }
    }

    private fun observeViewModel() {
        priceViewModel.listCommodity.observe(viewLifecycleOwner) {commodity ->
//            progressBar.visibility = View.GONE
            commodityAdapter.updateCommodities(commodity)
            Log.d("PriceFragment", "observeViewModel: $commodity")
        }

        priceViewModel.isLoadingCommodity.observe(viewLifecycleOwner) {
//            progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        priceViewModel.getListPrice()

    }

    private fun getCommodities(): ArrayList<Commodity> {
        val commodityPhoto = resources.obtainTypedArray(R.array.commodity_photos)
        val commodityNames = resources.getStringArray(R.array.commodity_name)
        val listCommodity = ArrayList<Commodity>()
        for (position in commodityNames.indices) {
            val commodityName = commodityNames[position]
            val commodityPrice = when (commodityName) {
                "Bawang Merah" -> priceViewModel.bawangPrice.value ?: 0
                "Cabe Rawit Merah" -> priceViewModel.cabePrice.value ?: 0
                "Jagung" -> priceViewModel.jagungPrice.value ?: 0
                "Kacang" -> priceViewModel.kacangPrice.value ?: 0
                "Kedelai" -> priceViewModel.kedelaiPrice.value ?: 0
                "Kentang" -> priceViewModel.kentangPrice.value ?: 0
                "Kol" -> priceViewModel.kolPrice.value ?: 0
                "Tomat" -> priceViewModel.tomatPrice.value ?: 0
                else -> 0
            }
            listCommodity.add(
                Commodity(
                    commodityPhoto.getResourceId(position, -1),
                    commodityName,
                    commodityPrice
                )
            )
        }
        commodityPhoto.recycle()

        return listCommodity
    }


    private fun getTopics(): List<Topics> {
        // Replace with your data fetching logic
        return listOf(
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow rice", "Learn how to grow rice in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow beans", "Learn how to grow beans in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow sugar", "Learn how to grow sugar in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow coffee", "Learn how to grow coffee in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow tea", "Learn how to grow tea in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow potatoes", "Learn how to grow potatoes in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time), false),
        )
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}