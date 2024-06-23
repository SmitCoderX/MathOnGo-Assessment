package com.smitcoderx.mathongoassignment.api

import com.smitcoderx.mathongoassignment.BuildConfig
import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.models.recipe.RecipeData
import com.smitcoderx.mathongoassignment.models.search.SearchData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReciipiieApi {

    @GET("recipes/random")
    suspend fun getPopularRecipes(
        @Query("apiKey") apiKey: String = BuildConfig.apiKey,
        @Query("number") number: Int = 10
    ): Response<RecipeData>

    @GET("recipes/complexSearch")
    suspend fun allRecipes(
        @Query("apiKey") apiKey: String = BuildConfig.apiKey,
        @Query("number") number: Int = 20,
        @Query("maxReadyTime") readyTime: Int = 60,
        @Query("addRecipeInformation") addInfo: Boolean = true,
    ): Response<SearchData>

    @GET("recipes/complexSearch")
    suspend fun searchItem(
        @Query("apiKey") apiKey: String = BuildConfig.apiKey,
        @Query("number") number: Int = 20,
        @Query("query") query: String,
        @Query("addRecipeInformation") addInfo: Boolean = true,
    ): Response<SearchData>

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String = BuildConfig.apiKey,
    ): Response<Recipe>

    @GET("recipes/{id}/nutritionWidget.json")
    suspend fun getNutritionDetails(
        @Path("id") id: String,
        @Query("apiKey") apiKey: String = BuildConfig.apiKey,
    ): Response<NutritionData>

    @GET("recipes/{id}/similar")
    suspend fun getSimilar(
        @Path("id") id: String,
        @Query("number") number: Int = 10,
        ): Response<RecipeData>


}