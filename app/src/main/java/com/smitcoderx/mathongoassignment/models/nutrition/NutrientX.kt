package com.smitcoderx.mathongoassignment.models.nutrition


import com.google.gson.annotations.SerializedName

data class NutrientX(
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("percentOfDailyNeeds")
    var percentOfDailyNeeds: Double? = null,
    @SerializedName("unit")
    var unit: String? = null
)