package com.smitcoderx.mathongoassignment.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.mathongoassignment.BuildConfig

import com.smitcoderx.mathongoassignment.R

import com.smitcoderx.mathongoassignment.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)



    }

}