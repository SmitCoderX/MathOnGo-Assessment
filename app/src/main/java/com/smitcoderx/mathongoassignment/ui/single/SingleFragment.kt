package com.smitcoderx.mathongoassignment.ui.single

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSingleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleFragment : Fragment(R.layout.fragment_single) {

    private lateinit var binding: FragmentSingleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBinding.bind(view)


    }

}