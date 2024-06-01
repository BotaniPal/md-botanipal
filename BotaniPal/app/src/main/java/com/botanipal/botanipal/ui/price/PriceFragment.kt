package com.botanipal.botanipal.ui.price

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.botanipal.botanipal.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class PriceFragment : Fragment() {

    companion object {
        fun newInstance() = PriceFragment()
    }

    private val viewModel: PriceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_price, container, false)
    }
}