package com.smitcoderx.mathongoassignment.models.nutrition


import com.google.gson.annotations.SerializedName

data class Property(
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("unit")
    var unit: String? = null
)