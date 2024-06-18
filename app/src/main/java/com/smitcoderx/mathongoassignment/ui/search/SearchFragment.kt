package com.smitcoderx.mathongoassignment.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSearchBinding
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel by viewModels<SearchViewModel>()
    private lateinit var searchRecipesAdapter: SearchRecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        searchRecipesAdapter = SearchRecipesAdapter()

        binding.editSearch.setupClearButtonWithAction()
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                searchViewModel.getQueryRecipe(s.toString())
            }
        })

        handleSearch()


    }

    private fun handleSearch() {
        searchViewModel.searchLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    searchRecipesAdapter.differ.submitList(it.data?.results)
                    binding.rvSearch.apply {
                        setHasFixedSize(true)
                        adapter = searchRecipesAdapter
                    }
                }

                is ResponseState.Loading -> {

                }

                is ResponseState.Error -> {

                }
            }
        }
    }

    fun EditText.setupClearButtonWithAction() {

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                binding.btnClear.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        })

        binding.btnClear.setOnClickListener {
            binding.editSearch.setText("")
        }

    }

}