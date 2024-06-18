package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Step(
    @SerializedName("equipment")
    var equipment: List<Equipment?>? = null,
    @SerializedName("ingredients")
    var ingredients: List<Ingredient?>? = null,
    @SerializedName("length")
    var length: Length? = null,
    @SerializedName("number")
    var number: Int? = null,
    @SerializedName("step")
    var step: String? = null
)