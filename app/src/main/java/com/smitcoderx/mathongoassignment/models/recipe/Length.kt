package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Length(
    @SerializedName("number")
    var number: Int? = null,
    @SerializedName("unit")
    var unit: String? = null
)