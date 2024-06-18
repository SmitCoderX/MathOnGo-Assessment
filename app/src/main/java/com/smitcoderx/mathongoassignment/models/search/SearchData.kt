package com.smitcoderx.mathongoassignment.models.search


import com.google.gson.annotations.SerializedName
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

data class SearchData(
    @SerializedName("number")
    var number: Int? = null,
    @SerializedName("offset")
    var offset: Int? = null,
    @SerializedName("results")
    var results: List<Recipe?>? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = null
)