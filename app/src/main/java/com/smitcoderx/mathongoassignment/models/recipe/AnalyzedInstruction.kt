package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class AnalyzedInstruction(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("steps")
    var steps: List<Step?>? = null
)