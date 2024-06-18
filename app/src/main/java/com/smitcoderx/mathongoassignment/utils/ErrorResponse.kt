package com.smitcoderx.mathongoassignment.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smitcoderx.mathongoassignment.models.error.ErrorResponse
import retrofit2.Response

fun <T> errorResponse(response: Response<T>): ErrorResponse? {
    val gson = Gson()
    val type = object : TypeToken<ErrorResponse?>() {}.type
    return gson.fromJson(response.errorBody()!!.charStream(), type)
}