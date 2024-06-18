package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Metric(
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("unitLong")
    var unitLong: String? = null,
    @SerializedName("unitShort")
    var unitShort: String? = null
)