package com.smitcoderx.mathongoassignment.ui.single

import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

data class SingleDataMapper(
    val recipe: Recipe,
    val nutrition: NutritionData
)