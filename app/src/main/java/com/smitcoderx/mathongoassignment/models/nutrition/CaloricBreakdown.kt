package com.smitcoderx.mathongoassignment.models.nutrition


import com.google.gson.annotations.SerializedName

data class CaloricBreakdown(
    @SerializedName("percentCarbs")
    var percentCarbs: Double? = null,
    @SerializedName("percentFat")
    var percentFat: Double? = null,
    @SerializedName("percentProtein")
    var percentProtein: Double? = null
)