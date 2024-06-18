package com.smitcoderx.mathongoassignment.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentHomeBinding
import com.smitcoderx.mathongoassignment.ui.home.adapter.AllRecipesAdapter
import com.smitcoderx.mathongoassignment.ui.home.adapter.PopularRecipesAdapter
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var popularRecipesAdapter: PopularRecipesAdapter
    private lateinit var allRecipeAdapter: AllRecipesAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        popularRecipesAdapter = PopularRecipesAdapter()
        allRecipeAdapter = AllRecipesAdapter()

        handleHomePage()

        binding.viewSearch.ccSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSearchFragment())
        }
    }

    private fun handleHomePage() {
        homeViewModel.homePageUi.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    hideLoading()
                    val firstName = it.data?.loggedInUser?.name?.split(" ")
                    binding.tvHomeSign.text = "👋 Hey ${firstName?.get(0)}"

                    popularRecipesAdapter.differ.submitList(it.data?.popularRecipes?.recipes)
                    allRecipeAdapter.differ.submitList(it.data?.allRecipes?.results)

                    binding.rvPopular.apply {
                        setHasFixedSize(true)
                        adapter = popularRecipesAdapter
                    }

                    binding.rvAllRecipe.apply {
                        setHasFixedSize(true)
                        adapter = allRecipeAdapter
                    }
                }

                is ResponseState.Loading -> {
                    showLoading()
                }

                is ResponseState.Error -> {
                    showLoading()
                    binding.loading.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = it.message
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            scrollView.visibility = View.GONE
            tvError.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.apply {
            loading.visibility = View.GONE
            tvError.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
        }
    }
}