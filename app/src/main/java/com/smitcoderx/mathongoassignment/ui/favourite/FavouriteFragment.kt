package com.smitcoderx.mathongoassignment.ui.favourite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentFavouriteBinding
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.ui.favourite.adapter.RecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite), RecipesAdapter.OnFavItemClick {

    private lateinit var binding: FragmentFavouriteBinding
    private val favViewModel by viewModels<FavouriteViewModel>()
    private lateinit var favouriteAdapter: RecipesAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouriteBinding.bind(view)

        favouriteAdapter = RecipesAdapter(this)
        favViewModel.getSavedList()

        favViewModel.favListLiveData.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.rvFav.visibility = View.GONE
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = "There are no Saved Recipes"
            } else {
                binding.tvError.visibility = View.GONE
                binding.rvFav.visibility = View.VISIBLE
                favouriteAdapter.differ.submitList(it)
                favouriteAdapter.notifyDataSetChanged()
                binding.rvFav.apply {
                    setHasFixedSize(false)
                    adapter = favouriteAdapter
                }
            }

        }


    }

    override fun onFavClick(item: Recipe) {
        val action =
            FavouriteFragmentDirections.actionFavouriteFragmentToSingleFragment(item.id.toString())
        findNavController().navigate(action)
    }

}