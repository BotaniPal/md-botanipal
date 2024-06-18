package com.botanipal.botanipal.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.botanipal.botanipal.ui.BookmarkTabFragment

class BookmarkSectionPagerAdapter(activity: FragmentActivity, private var title: String) : FragmentStateAdapter(activity){
    override fun createFragment(position: Int): Fragment {
        val fragment = BookmarkTabFragment()
        fragment.arguments = Bundle().apply {
            putInt(BookmarkTabFragment.ARG_SECTION_NUMBER, position + 1)
            putString(BookmarkTabFragment.ARG_TITLE, title)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}