package com.smitcoderx.mathongoassignment.ui.searchbottom

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSingleBottomSheetBinding
import com.smitcoderx.mathongoassignment.ui.single.SingleDataMapper
import com.smitcoderx.mathongoassignment.ui.single.SingleRecipeViewModel
import com.smitcoderx.mathongoassignment.ui.single.adapter.IngredientsAdapter
import com.smitcoderx.mathongoassignment.utils.Constants
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_single_bottom_sheet) {

    private lateinit var binding: FragmentSingleBottomSheetBinding
    private val viewModel: SingleRecipeViewModel by viewModels()
    private val args by navArgs<SingleBottomSheetFragmentArgs>()
    private var isFav = false
    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBottomSheetBinding.bind(view)

        val id = args.id
        viewModel.getSingleData(id)
        ingredientsAdapter = IngredientsAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        handleHeaderClicks()

        binding.btnGetIngredients.setOnClickListener {
            handleBtnClick()
        }
        viewModel.favData.observe(viewLifecycleOwner) {
            Log.d(Constants.TAG, "onViewCreated: $it")
            isFav = it
            if (it) {
                addFavourite()
            } else {
                removeFavourite()
            }
        }

        viewModel.recipeData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    if (it.data?.recipe?.isFavourite == true) {
                        addFavourite()
                    } else {
                        removeFavourite()
                    }
                    it.data?.let { it1 -> handleFav(it1) }

                    binding.tvRecipe.text = it.data?.recipe?.title
                    binding.ivRecipe.load(it.data?.recipe?.image) {
                        crossfade(true)
                    }
                    binding.tvReadyMin.text = it.data?.recipe?.readyInMinutes.toString()
                    binding.tvServing.text = it.data?.recipe?.servings.toString()
                    binding.tvPriceServing.text = it.data?.recipe?.pricePerServing.toString()

                    ingredientsAdapter.differ.submitList(it.data?.recipe?.extendedIngredients)
                    binding.rvIngredients.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(requireContext(), 3)
                        adapter = ingredientsAdapter
                    }


                }

                is ResponseState.Loading -> {

                }

                is ResponseState.Error -> {

                }
            }
        }

    }

    private fun addFavourite() {
        binding.ivFav.isLiked = true
    }


    private fun removeFavourite() {
        binding.ivFav.isLiked = false
    }

    private fun handleFav(mapped: SingleDataMapper) {
        binding.ivFav.setOnClickListener {
            if (isFav) {
                viewModel.handleFavourite(mapped.recipe, mapped.nutrition, false)
                removeFavourite()
            } else {
                viewModel.handleFavourite(mapped.recipe, mapped.nutrition, true)
                addFavourite()
            }
        }
    }

    private fun handleBtnClick() {
        when (binding.btnGetIngredients.text) {
            ContextCompat.getString(requireContext(), R.string.get_ingredients) -> {
                binding.coRecipe.visibility = View.GONE
                binding.coIngredients.visibility = View.VISIBLE
                binding.btnGetIngredients.text =
                    ContextCompat.getString(requireContext(), R.string.get_full_recipe)
            }

            ContextCompat.getString(requireContext(), R.string.get_full_recipe) -> {
                binding.rvIngredients.visibility = View.GONE
                binding.coFullRecipe.visibility = View.VISIBLE
                binding.btnGetIngredients.text =
                    ContextCompat.getString(requireContext(), R.string.get_similar_recipe)
            }

            ContextCompat.getString(requireContext(), R.string.get_similar_recipe) -> {
                binding.fullRecipe.root.visibility = View.GONE
                binding.coSimilarRecipe.visibility = View.VISIBLE
                binding.llBtn.visibility = View.GONE
            }

            else -> {
                binding.btnGetIngredients.text =
                    ContextCompat.getString(requireContext(), R.string.get_ingredients)
                binding.llBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun handleHeaderClicks() {
        binding.tvRecipe.setOnClickListener {
            binding.coRecipe.visibility = View.VISIBLE
            binding.coIngredients.visibility = View.GONE
            binding.coFullRecipe.visibility = View.GONE
            binding.coSimilarRecipe.visibility = View.GONE
            binding.btnGetIngredients.text =
                ContextCompat.getString(requireContext(), R.string.get_ingredients)
            binding.llBtn.visibility = View.VISIBLE
        }

        binding.coIngredients.setOnClickListener {
            binding.coRecipe.visibility = View.GONE
            binding.rvIngredients.visibility = View.VISIBLE
            binding.coFullRecipe.visibility = View.GONE
            binding.coSimilarRecipe.visibility = View.GONE
            binding.btnGetIngredients.text =
                ContextCompat.getString(requireContext(), R.string.get_full_recipe)
            binding.llBtn.visibility = View.VISIBLE
        }

        binding.coFullRecipe.setOnClickListener {
            binding.coRecipe.visibility = View.GONE
            binding.rvIngredients.visibility = View.GONE
            binding.fullRecipe.root.visibility = View.VISIBLE
            binding.coSimilarRecipe.visibility = View.GONE
            binding.btnGetIngredients.text =
                ContextCompat.getString(requireContext(), R.string.get_similar_recipe)
            binding.llBtn.visibility = View.VISIBLE
        }

    }


}