package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class RecipeData(
    @SerializedName("recipes")
    var recipes: List<Recipe?>? = null
)