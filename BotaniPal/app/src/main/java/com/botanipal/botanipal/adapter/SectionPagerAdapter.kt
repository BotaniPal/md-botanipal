package com.botanipal.botanipal.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.botanipal.botanipal.ui.TabFragment

class SectionPagerAdapter(activity: FragmentActivity, private var title: String) : FragmentStateAdapter(activity){
    override fun createFragment(position: Int): Fragment {
        val fragment = TabFragment()
        fragment.arguments = Bundle().apply {
            putInt(TabFragment.ARG_SECTION_NUMBER, position + 1)
            putString(TabFragment.ARG_TITLE, title)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}