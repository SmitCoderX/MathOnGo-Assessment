package com.smitcoderx.mathongoassignment.ui.searchbottom

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textview.MaterialTextView
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSingleBottomSheetBinding
import com.smitcoderx.mathongoassignment.models.nutrition.Bad
import com.smitcoderx.mathongoassignment.models.nutrition.Good
import com.smitcoderx.mathongoassignment.models.nutrition.NutrientX
import com.smitcoderx.mathongoassignment.models.recipe.AnalyzedInstruction
import com.smitcoderx.mathongoassignment.models.recipe.Equipment
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.ui.favourite.adapter.RecipesAdapter
import com.smitcoderx.mathongoassignment.ui.single.SingleDataMapper
import com.smitcoderx.mathongoassignment.ui.single.SingleRecipeViewModel
import com.smitcoderx.mathongoassignment.ui.single.adapter.EquipmentAdapter
import com.smitcoderx.mathongoassignment.ui.single.adapter.IngredientsAdapter
import com.smitcoderx.mathongoassignment.utils.Constants
import com.smitcoderx.mathongoassignment.utils.Constants.TAG
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_single_bottom_sheet),
    RecipesAdapter.OnFavItemClick {

    private lateinit var binding: FragmentSingleBottomSheetBinding
    private val viewModel: SingleRecipeViewModel by viewModels()
    private val args by navArgs<SingleBottomSheetFragmentArgs>()
    private var isFav = false
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var equipmentAdapter: EquipmentAdapter
    private lateinit var recipeAdapter: RecipesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBottomSheetBinding.bind(view)
        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior

        val id = args.id
        viewModel.getSingleData(id)

        ingredientsAdapter = IngredientsAdapter()
        equipmentAdapter = EquipmentAdapter()
        recipeAdapter = RecipesAdapter(this)


        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        handleHeaderClicks()

        handleOnDropDown(
            binding.fullRecipe.llNutrition,
            binding.fullRecipe.ivDrop,
            binding.fullRecipe.tvNutrition
        )
        handleOnDropDown(
            binding.fullRecipe.llBadNutrition,
            binding.fullRecipe.ivDropBad,
            binding.fullRecipe.tvBadNutrition
        )
        handleOnDropDown(
            binding.fullRecipe.llGoodNutrition,
            binding.fullRecipe.ivDropGood,
            binding.fullRecipe.tvGoodNutrition
        )

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

                    recipeAdapter.differ.submitList(it.data?.similar)
                    binding.rvSimilar.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        adapter = recipeAdapter
                    }

                    binding.fullRecipe.tvInstruments.text =
                        Html.fromHtml(it.data?.recipe?.instructions, Html.FROM_HTML_MODE_LEGACY)
                            .toString()
                    equipmentAdapter.differ.submitList(filterEquipments(it.data?.recipe?.analyzedInstructions))
                    binding.fullRecipe.rvEquipments.apply {
                        setHasFixedSize(true)
                        adapter = equipmentAdapter
                    }
                    binding.fullRecipe.tvQuickSummary.text =
                        Html.fromHtml(it.data?.recipe?.summary, Html.FROM_HTML_MODE_LEGACY)
                            .toString()
                    binding.fullRecipe.tvNutrition.text =
                        filterNutrients(it.data?.nutrition?.nutrients).joinToString(", ")
                    binding.fullRecipe.tvBadNutrition.text =
                        filterListBad(it.data?.nutrition?.bad).joinToString(", ")
                    binding.fullRecipe.tvGoodNutrition.text = filterListGood(
                        it.data?.nutrition?.good
                    ).joinToString(", ")


                }

                is ResponseState.Loading -> {

                }

                is ResponseState.Error -> {
                    Log.d(TAG, "ERROR: ${it.message}")
                    Log.d(TAG, "ERROR SIMI: ${it.data?.similar}")
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
                binding.fullRecipe.root.visibility = View.VISIBLE
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

    private fun filterEquipments(analyzedInstructions: List<AnalyzedInstruction?>?): List<Equipment> {
        val equipList = mutableListOf<Equipment>()
        analyzedInstructions?.forEach {
            it?.steps?.forEach { step ->
                step?.equipment?.forEach { equipment ->
                    equipment?.let { it1 -> equipList.add(it1) }
                }
            }
        }
        return equipList
    }

    private fun handleOnDropDown(
        layout: LinearLayout, dropImage: AppCompatImageView, textView: MaterialTextView
    ) {
        var drop = false
        layout.setOnClickListener {
            if (drop) {
                drop = false
                TransitionManager.beginDelayedTransition(layout, AutoTransition())
                dropImage.animate().rotation(0f)
                textView.visibility = View.GONE
            } else {
                drop = true
                TransitionManager.beginDelayedTransition(layout, AutoTransition())
                dropImage.animate().rotation(180f)
                textView.visibility = View.VISIBLE
            }
        }
    }

    private fun filterNutrients(analyzedInstructions: List<NutrientX?>?): List<String> {
        val nutrients = mutableListOf<String>()
        analyzedInstructions?.forEach {
            nutrients.add(it?.name.toString())
        }
        return nutrients
    }

    private fun filterListBad(list: List<Bad?>?): List<String> {
        val badList = mutableListOf<String>()
        list?.forEach {
            badList.add(it?.title.toString())
        }
        return badList
    }

    private fun filterListGood(list: List<Good?>?): List<String> {
        val goodList = mutableListOf<String>()
        list?.forEach {
            goodList.add(it?.title.toString())
        }
        return goodList
    }

    override fun onFavClick(item: Recipe) {

    }


}