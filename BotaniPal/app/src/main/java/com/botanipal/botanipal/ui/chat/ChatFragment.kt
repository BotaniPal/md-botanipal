package com.botanipal.botanipal.ui.chat

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.viewpager2.widget.ViewPager2
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.SectionPagerAdapter
import com.botanipal.botanipal.databinding.FragmentChatBinding
import com.botanipal.botanipal.ui.ViewModelFactory
import com.botanipal.botanipal.ui.home.HomeFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database

class ChatFragment : Fragment() {
    private val viewModel: ChatViewModel by viewModels()
    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatDB: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


//        val title = requireActivity().intent.getStringExtra("TITLE") ?: "BotaniPal"
//        setupViewPager(title)
//
//        chatDB = Firebase.database
//        val messagesRef = chatDB.reference.child(MESSAGES_CHILD)


        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Use the ViewModel
        val chatViewModel = ViewModelFactory.getInstance(requireActivity().application).create(ChatViewModel::class.java)

//        _binding = FragmentChatBinding.inflate(inflater, container, false)
//        val root: View = binding.root

        val title = requireActivity().intent.getStringExtra("TITLE") ?: "BotaniPal"
        setupViewPager(view, title)

    }


    private fun setupViewPager(view: View, title: String) {
        val sectionsPagerAdapter = SectionPagerAdapter(requireActivity(), title)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)

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
            R.string.tab_text_1_chat,
            R.string.tab_text_2_chat
        )

        fun newInstance() = ChatFragment()
    }
}