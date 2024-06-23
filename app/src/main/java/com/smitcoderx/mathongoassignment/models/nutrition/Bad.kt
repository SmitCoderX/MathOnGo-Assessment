package com.smitcoderx.mathongoassignment.models.nutrition


import com.google.gson.annotations.SerializedName

data class Bad(
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("indented")
    var indented: Boolean? = null,
    @SerializedName("percentOfDailyNeeds")
    var percentOfDailyNeeds: Double? = null,
    @SerializedName("title")
    var title: String? = null
)