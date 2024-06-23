package com.smitcoderx.mathongoassignment.ui.single

import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.google.android.material.textview.MaterialTextView
import com.smitcoderx.mathongoassignment.R
import com.smitcoderx.mathongoassignment.databinding.FragmentSingleBinding
import com.smitcoderx.mathongoassignment.models.nutrition.Bad
import com.smitcoderx.mathongoassignment.models.nutrition.Good
import com.smitcoderx.mathongoassignment.models.nutrition.NutrientX
import com.smitcoderx.mathongoassignment.models.recipe.AnalyzedInstruction
import com.smitcoderx.mathongoassignment.models.recipe.Equipment
import com.smitcoderx.mathongoassignment.ui.single.adapter.EquipmentAdapter
import com.smitcoderx.mathongoassignment.ui.single.adapter.IngredientsAdapter
import com.smitcoderx.mathongoassignment.utils.Constants.TAG
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleFragment : Fragment(R.layout.fragment_single) {

    private lateinit var binding: FragmentSingleBinding
    private val singleViewModel: SingleRecipeViewModel by viewModels()
    private val args by navArgs<SingleFragmentArgs>()
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var equipmentAdapter: EquipmentAdapter
    private var isFav = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSingleBinding.bind(view)
        val id = args.id
        singleViewModel.getSingleData(id)

        ingredientsAdapter = IngredientsAdapter()
        equipmentAdapter = EquipmentAdapter()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        singleViewModel.favData.observe(viewLifecycleOwner) {
            Log.d(TAG, "onViewCreated: $it")
            isFav = it
            if (it) {
                addFavourite()
            } else {
                removeFavourite()
            }
        }

        handleOnDropDown(binding.llNutrition, binding.ivDrop, binding.tvNutrition)
        handleOnDropDown(binding.llBadNutrition, binding.ivDropBad, binding.tvBadNutrition)
        handleOnDropDown(binding.llGoodNutrition, binding.ivDropGood, binding.tvGoodNutrition)

        singleViewModel.recipeData.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    hideLoading()
                    binding.ivRecipe.load(it.data?.recipe?.image) {
                        crossfade(true)
                    }
                    binding.tvReadyMin.text = it.data?.recipe?.readyInMinutes.toString()
                    binding.tvServing.text = it.data?.recipe?.servings.toString()
                    binding.tvPriceServing.text = it.data?.recipe?.pricePerServing.toString()
                    ingredientsAdapter.differ.submitList(it.data?.recipe?.extendedIngredients)
                    binding.rvIngredients.apply {
                        setHasFixedSize(true)
                        adapter = ingredientsAdapter
                    }
                    binding.tvInstruments.text =
                        Html.fromHtml(it.data?.recipe?.instructions, Html.FROM_HTML_MODE_LEGACY)
                            .toString()
                    equipmentAdapter.differ.submitList(filterEquipments(it.data?.recipe?.analyzedInstructions))
                    binding.rvEquipments.apply {
                        setHasFixedSize(true)
                        adapter = equipmentAdapter
                    }
                    binding.tvQuickSummary.text =
                        Html.fromHtml(it.data?.recipe?.summary, Html.FROM_HTML_MODE_LEGACY)
                            .toString()
                    binding.tvNutrition.text =
                        filterNutrients(it.data?.nutrition?.nutrients).joinToString(", ")
                    binding.tvBadNutrition.text =
                        filterListBad(it.data?.nutrition?.bad).joinToString(", ")
                    binding.tvGoodNutrition.text = filterListGood(
                        it.data?.nutrition?.good
                    ).joinToString(", ")

                    if (it.data?.recipe?.isFavourite == true) {
                        addFavourite()
                    } else {
                        removeFavourite()
                    }

                    it.data?.let { it1 -> handleFav(it1) }

                }

                is ResponseState.Loading -> {
                    showLoading()
                }

                is ResponseState.Error -> {
                    hideLoading()
                    binding.loading.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = it.message
                }
            }
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


    private fun showLoading() {
        binding.apply {
            scrollSingle.visibility = View.GONE
            tvError.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.apply {
            loading.visibility = View.GONE
            tvError.visibility = View.GONE
            scrollSingle.visibility = View.VISIBLE
        }
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

    private fun addFavourite() {
       binding.ivFav.isLiked = true
    }


    private fun removeFavourite() {
        binding.ivFav.isLiked = false
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

    private fun handleFav(mapped: SingleDataMapper) {
        binding.ivFav.setOnClickListener {
            if (isFav) {
                singleViewModel.handleFavourite(mapped.recipe, mapped.nutrition, false)
                removeFavourite()
            } else {
                singleViewModel.handleFavourite(mapped.recipe, mapped.nutrition, true)
                addFavourite()
            }
        }
    }
}