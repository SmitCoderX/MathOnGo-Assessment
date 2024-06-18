package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class Equipment(
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("localizedName")
    var localizedName: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("temperature")
    var temperature: Temperature? = null
)