package com.botanipal.botanipal.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.botanipal.botanipal.ui.ChatTabFragment

class ChatSectionPagerAdapter(activity: FragmentActivity, private var title: String) : FragmentStateAdapter(activity){
    override fun createFragment(position: Int): Fragment {
        val fragment = ChatTabFragment()
        fragment.arguments = Bundle().apply {
            putInt(ChatTabFragment.ARG_SECTION_NUMBER, position + 1)
            putString(ChatTabFragment.ARG_TITLE, title)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}