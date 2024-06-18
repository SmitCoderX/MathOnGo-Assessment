package com.smitcoderx.mathongoassignment.models.error


import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    var code: Int? = null,
    @SerializedName("message")
    var message: String? = null,
    @SerializedName("status")
    var status: String? = null
)