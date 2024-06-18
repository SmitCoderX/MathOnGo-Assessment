package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Measures(
    @SerializedName("metric")
    var metric: Metric? = null,
    @SerializedName("us")
    var us: Us? = null
)