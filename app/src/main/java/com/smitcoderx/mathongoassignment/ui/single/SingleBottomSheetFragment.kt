package com.smitcoderx.mathongoassignment.ui.single

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSingleBottomSheetBinding

class SingleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_single_bottom_sheet) {

    private lateinit var binding: FragmentSingleBottomSheetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBottomSheetBinding.bind(view)


    }

}