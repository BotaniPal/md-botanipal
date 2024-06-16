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
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.ChatanisAdapter
import com.botanipal.botanipal.adapter.ForumAdapter
import com.botanipal.botanipal.data.model.Topics
import com.botanipal.botanipal.databinding.FragmentTabBinding
import com.botanipal.botanipal.ui.chat.forums.ForumsActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [TabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabFragment : Fragment() {
    private var _binding: FragmentTabBinding? = null
    private val binding get() = _binding
    private lateinit var progressBar: ProgressBar
    private lateinit var forumAdapter: ForumAdapter
    private lateinit var chatanisAdapter: ChatanisAdapter
    private val viewModel: TabViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding?.root ?: inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding?.fragmentProgressBar ?: view.findViewById(R.id.fragmentProgressBar)
        progressBar.visibility = View.VISIBLE

        forumAdapter = ForumAdapter(emptyList())
        chatanisAdapter = ChatanisAdapter(emptyList())

        var recyclerView: RecyclerView = view.findViewById(R.id.rv_user_follow)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val title = arguments?.getString(ARG_TITLE) ?: "BotaniPal"

        if (sectionNumber == 1) {
            progressBar.visibility = View.VISIBLE
            recyclerView.adapter =chatanisAdapter

            if(viewModel.chatanis.value.isNullOrEmpty()){
                viewModel.searchChatanis()
            }
        } else {
            progressBar.visibility = View.VISIBLE
            recyclerView.adapter = forumAdapter

            forumAdapter.setOnItemClickCallback(object : ForumAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Topics) {
                    val intent = Intent(requireContext(), ForumsActivity::class.java).apply {
                        putExtra(ARG_TITLE, data.title)
                    }
                    startActivity(intent)
                }
            })

            viewModel.topic.observe(viewLifecycleOwner) {
                forumAdapter.updateForum(it)
                progressBar.visibility = View.GONE
            }

            if(viewModel.chatanis.value.isNullOrEmpty()){
                viewModel.searchTopics()
            }
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
        const val ARG_TITLE = "TITLE"

        @JvmStatic
        fun newInstance(sectionNumber: Int, title: String) =
            TabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_TITLE, title)
                }
            }
    }
}