package com.botanipal.botanipal.ui.bookmark

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.ChatSectionPagerAdapter
import com.botanipal.botanipal.adapter.PlantAdapter
import com.botanipal.botanipal.data.response.ScanData
import com.botanipal.botanipal.databinding.FragmentBookmarkBinding
import com.botanipal.botanipal.ui.scan.ResultActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BookmarkFragment : Fragment() {
    private val viewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null

    private val binding get() = _binding
    private lateinit var adapter: PlantAdapter
    private lateinit var progressBar: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PlantAdapter(emptyList())
        progressBar = view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_bookmark)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

//        adapter.setOnItemClickCallback(object : PlantAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: ScanData) {
//                val intent = Intent(requireContext(), ResultActivity::class.java).apply {
//                    putExtra(ARG_TITLE, data.prediction)
//                }
//                startActivity(intent)
//            }
//        })

//        viewModel.

        viewModel.fetchBookmark()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }


    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_3_chat,
            R.string.tab_text_4_chat
        )
        fun newInstance() = BookmarkFragment()
    }
}