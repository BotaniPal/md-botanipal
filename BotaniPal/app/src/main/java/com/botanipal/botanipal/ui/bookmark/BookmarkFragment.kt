package com.botanipal.botanipal.ui.bookmark

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.BookmarkSectionPagerAdapter
import com.botanipal.botanipal.adapter.ChatSectionPagerAdapter
import com.botanipal.botanipal.databinding.FragmentBookmarkBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BookmarkFragment : Fragment() {
    private val viewModel: BookmarkViewModel by viewModels()
    private var _binding: FragmentBookmarkBinding? = null

    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Use the ViewModel

        val title = requireActivity().intent.getStringExtra("TITLE") ?: "BotaniPal"
        setupViewPager(view, title)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }
    private fun setupViewPager(view: View, title: String) {
        val sectionsPagerAdapter = BookmarkSectionPagerAdapter(requireActivity(), title)
        val viewPager: ViewPager2 = view.findViewById(R.id.bookmark_view_pager)

        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = requireView().findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
            viewModel.setIndex(position)
        }.attach()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.elevation = 0f
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