package com.smitcoderx.mathongoassignment.ui.home

import com.google.firebase.auth.FirebaseAuth
import com.smitcoderx.mathongoassignment.api.ReciipiieApi
import com.smitcoderx.mathongoassignment.models.recipe.RecipeData
import com.smitcoderx.mathongoassignment.models.search.SearchData
import com.smitcoderx.mathongoassignment.models.user.UserData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import com.smitcoderx.mathongoassignment.utils.errorResponse
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val api: ReciipiieApi
) {
    fun getSignedInUser(): ResponseState<UserData> {
        return if (auth.currentUser != null) {
            ResponseState.Success(auth.currentUser?.run {
                UserData(
                    id = uid,
                    name = displayName,
                    email = email,
                    profileUrl = photoUrl?.toString()
                )
            }
            )
        } else {
            ResponseState.Error("Something Went Wrong")
        }
    }

    suspend fun getPopularRecipes(): ResponseState<RecipeData> {
        val response = api.getPopularRecipes()
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

    suspend fun getAllRecipes(): ResponseState<SearchData> {
        val response = api.allRecipes()
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