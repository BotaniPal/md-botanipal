package com.botanipal.botanipal.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.ChatAdapter
import com.botanipal.botanipal.adapter.PlantAdapter
import com.botanipal.botanipal.data.response.DataItem
import com.botanipal.botanipal.data.response.ScanData
import com.botanipal.botanipal.databinding.FragmentBookmarkTabBinding
import com.botanipal.botanipal.ui.scan.ResultActivity

class BookmarkTabFragment : Fragment() {
    private var _binding: FragmentBookmarkTabBinding? = null
    private val binding get() = _binding
    private lateinit var progressBar: ProgressBar
    private lateinit var plantAdapter: PlantAdapter
//    private lateinit var chatAdapter: ChatAdapter
//    private val viewModel: BookmarkTabViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding?.fragmentProgressBar ?: view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        plantAdapter = PlantAdapter(emptyList())

        val recyclerView = binding?.rvUserFollow ?: view.findViewById(R.id.rv_user_follow)
        recyclerView.layoutManager = LinearLayoutManager(context)

//        val sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER, 0)
//        val title = arguments?.getString(ARG_TITLE) ?: "BotaniPal"

//        if (sectionNumber == 1) {
            progressBar.visibility = View.VISIBLE
            recyclerView.adapter = plantAdapter

            plantAdapter.setOnItemClickCallback(object : PlantAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DataItem) {
                    val intent = Intent(requireContext(), ResultActivity::class.java).apply {
//                        putExtra(ARG_TITLE, data.prediction)
                    }
                    startActivity(intent)
                }
            })

//            viewModel.plantType.observe(viewLifecycleOwner) {
//                plantAdapter.updatePlants(it)
//                progressBar.visibility = View.GONE
//            }
//
//            if(viewModel.plantType.value.isNullOrEmpty()) {
//                viewModel.getBookmark()
//            }
//        } else {
//            progressBar.visibility = View.VISIBLE
//            recyclerView.adapter = plantAdapter
//
//            plantAdapter.setOnItemClickCallback(object : PlantAdapter.OnItemClickCallback {
//                override fun onItemClicked(data: ScanData) {
//                    val intent = Intent(requireContext(), ResultActivity::class.java).apply {
//                        putExtra(ARG_TITLE, data.prediction)
//                    }
//                    startActivity(intent)
//                }
//            })
//
//            viewModel.plantDisease.observe(viewLifecycleOwner) {
//                plantAdapter.updatePlants(it)
//                progressBar.visibility = View.GONE
//            }
//
//            if(viewModel.plantDisease.value.isNullOrEmpty()) {
//                viewModel.getBookmarkDisease()
//            }
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookmarkTabBinding.inflate(inflater, container, false)
        return binding?.root ?: inflater.inflate(R.layout.fragment_bookmark_tab, container, false)
    }

    companion object {
//        const val ARG_SECTION_NUMBER = "section_number"
//        const val ARG_TITLE = "TITLE"

//        @JvmStatic
//        fun newInstance(sectionNumber: Int, title: String) =
//            BookmarkTabFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ChatTabFragment.ARG_SECTION_NUMBER, sectionNumber)
//                    putString(ChatTabFragment.ARG_TITLE, title)
//                }
//            }
    }
}