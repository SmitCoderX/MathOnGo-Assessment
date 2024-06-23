package com.smitcoderx.mathongoassignment.ui.single

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.utils.Constants
import com.smitcoderx.mathongoassignment.utils.Constants.TAG
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleRecipeViewModel @Inject constructor(
    private val singleRecipeRepository: SingleRecipeRepository
) : ViewModel() {

    private val _recipeData = MutableLiveData<ResponseState<SingleDataMapper>>()
    val recipeData: LiveData<ResponseState<SingleDataMapper>> = _recipeData

    private val _favData = MutableLiveData<Boolean>()
    val favData: LiveData<Boolean> = _favData

    fun getSingleData(id: String) = viewModelScope.launch {
        _recipeData.value = ResponseState.Loading()
        val nutritionData = singleRecipeRepository.getNutrientsDetails(id)
        val recipeData = singleRecipeRepository.getRecipeData(id)
        val similar = singleRecipeRepository.getSimilarData(id)
        if (recipeData.data != null && nutritionData.data != null) {
            val savedData = singleRecipeRepository.getRecipeById(recipeData.data.id.toString())
            recipeData.data.extendedIngredients?.map {
                it?.image = Constants.IMAGE_BASE_URL + it?.image
            }
            nutritionData.data.recipeId = recipeData.data.id.toString()
            if (savedData != null) {
                recipeData.data.isFavourite =
                    savedData.id.toString() == recipeData.data.id.toString()
            }

            _recipeData.value = ResponseState.Success(
                SingleDataMapper(
                    recipe = recipeData.data, nutrition = nutritionData.data,
                    similar = similar.data?.recipes
                )
            )
        } else {
            _recipeData.value = ResponseState.Error(recipeData.message)
        }
    }


    private fun saveRecipe(recipe: Recipe, nutritionData: NutritionData) = viewModelScope.launch {
        singleRecipeRepository.insertRecipe(recipe, nutritionData)
    }

    private fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        singleRecipeRepository.deleteRecipe(recipe)
    }

    fun handleFavourite(recipeData: Recipe, nutritionData: NutritionData, isFav: Boolean) =
        viewModelScope.launch {
//        val nutritionData = singleRecipeRepository.getNutrientsById(id)
//        val recipeData = singleRecipeRepository.getRecipeById(id)

            if (isFav) {
                Log.d(TAG, "saved: ")
                saveRecipe(recipeData, nutritionData)
                _favData.value = true
            } else {
                Log.d(TAG, "removed: ")
                deleteRecipe(recipeData)
                _favData.value = false
            }
        }
}