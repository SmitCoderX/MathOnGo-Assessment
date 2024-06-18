package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("number")
    var number: Double? = null,
    @SerializedName("unit")
    var unit: String? = null
)