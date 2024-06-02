package com.botanipal.botanipal.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.botanipal.botanipal.adapter.ForumAdapter
import com.botanipal.botanipal.adapter.TopCommodityAdapter
import com.botanipal.botanipal.data.Commodity
import com.botanipal.botanipal.data.Topics
import com.botanipal.botanipal.databinding.FragmentHomeBinding
import com.botanipal.botanipal.ui.ViewModelFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var commodityAdapter: TopCommodityAdapter
    private lateinit var topicAdapter: ForumAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelFactory.getInstance(requireActivity().application).create(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        commodityAdapter = TopCommodityAdapter(getCommodities())
        topicAdapter = ForumAdapter(getTopics())

        val commodityRecyclerView = binding.rvComodity
        commodityRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        commodityRecyclerView.adapter = commodityAdapter

        val topicRecyclerView = binding.rvTopics
        topicRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        topicRecyclerView.adapter = topicAdapter

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



        return root
    }

    private fun getCommodities(): List<Commodity> {
        // Replace with your data fetching logic
        return listOf(
            Commodity("Corn", "Rp 8,238/kg"),
            Commodity("Wheat", "Rp 7,500/kg"),
            Commodity("Rice", "Rp 9,000/kg"),
            Commodity("Beans", "Rp 6,800/kg"),
            Commodity("Sugar", "Rp 1,500/kg"),
            Commodity("Coffee", "Rp 12,500/kg"),
            Commodity("Tea", "Rp 10,000/kg"),
            // Add more commodities here
        )
    }

    private fun getTopics(): List<Topics> {
        // Replace with your data fetching logic
        return listOf(
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow rice", "Learn how to grow rice in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow beans", "Learn how to grow beans in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow sugar", "Learn how to grow sugar in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow coffee", "Learn how to grow coffee in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow tea", "Learn how to grow tea in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow potatoes", "Learn how to grow potatoes in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow corn", "Learn how to grow corn in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
            Topics("How to grow wheat", "Learn how to grow wheat in your backyard", Timestamp(SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse("2024-01-04 15:00:00")!!.time)),
        )
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}