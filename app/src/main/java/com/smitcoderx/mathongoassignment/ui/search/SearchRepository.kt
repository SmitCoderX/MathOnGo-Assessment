package com.smitcoderx.mathongoassignment.ui.search

import com.google.firebase.auth.FirebaseAuth
import com.smitcoderx.mathongoassignment.api.ReciipiieApi
import com.smitcoderx.mathongoassignment.models.search.SearchData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import com.smitcoderx.mathongoassignment.utils.errorResponse
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val api: ReciipiieApi
) {

    suspend fun getSearchQuery(query: String): ResponseState<SearchData> {
        val response = api.searchItem(query = query)
        return try {
            if (response.isSuccessful) {
                ResponseState.Success(response.body())
            } else {
                ResponseState.Error(errorResponse(response)?.message.toString())
            }
        } catch (e: IOException) {
            ResponseState.Error(e.message)
        }
    }
}