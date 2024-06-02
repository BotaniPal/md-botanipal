package com.botanipal.botanipal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.botanipal.botanipal.R
import com.botanipal.botanipal.databinding.FragmentTabBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding?.root ?: inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        progressBar = requireActivity().findViewById(R.id.fragmentProgressBar)
//        progressBar.visibility = View.VISIBLE

        val sectionNumber = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        val title = arguments?.getString(ARG_TITLE) ?: "BotaniPal"

        if (sectionNumber == 1) {
            binding?.textView?.text = "This is section $sectionNumber for $title"
        } else {
            binding?.textView?.text = "This is section $sectionNumber for $title"
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