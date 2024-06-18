package com.smitcoderx.mathongoassignment.models.recipe


import com.google.gson.annotations.SerializedName

data class ExtendedIngredient(
    @SerializedName("aisle")
    var aisle: String? = null,
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("consistency")
    var consistency: String? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("measures")
    var measures: Measures? = null,
    @SerializedName("meta")
    var meta: List<String?>? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("nameClean")
    var nameClean: String? = null,
    @SerializedName("original")
    var original: String? = null,
    @SerializedName("originalName")
    var originalName: String? = null,
    @SerializedName("unit")
    var unit: String? = null
)