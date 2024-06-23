package com.smitcoderx.mathongoassignment.models.nutrition


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("amount")
    var amount: Double? = 0.0,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("nutrients")
    var nutrients: List<NutrientX>? = listOf(),
    @SerializedName("unit")
    var unit: String? = ""
)