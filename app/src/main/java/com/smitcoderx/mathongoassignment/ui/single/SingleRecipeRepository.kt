package com.smitcoderx.mathongoassignment.ui.single

import com.smitcoderx.mathongoassignment.api.ReciipiieApi
import com.smitcoderx.mathongoassignment.db.ReciipiieDao
import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.models.recipe.RecipeData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import com.smitcoderx.mathongoassignment.utils.errorResponse
import okio.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleRecipeRepository @Inject constructor(
    private val api: ReciipiieApi, private val recipeDao: ReciipiieDao
) {
    suspend fun getRecipeData(id: String): ResponseState<Recipe> {
        val response = api.getRecipeDetails(id = id)
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

    suspend fun getNutrientsDetails(id: String): ResponseState<NutritionData> {
        val response = api.getNutritionDetails(id = id)
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

    suspend fun insertRecipe(recipe: Recipe, nutrition: NutritionData) {
        recipeDao.insertRecipe(recipe)
        val nutrientsData = recipeDao.getNutrientsById(nutrition.recipeId)
        recipeDao.insertNutrients(nutrientsData ?: nutrition)
    }

    suspend fun getRecipeById(id: String): Recipe? {
        return recipeDao.getRecipeById(id)
    }

    suspend fun getNutrientsById(id: String): NutritionData? {
        return recipeDao.getNutrientsById(id)
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        val nutrientsData = recipeDao.getNutrientsById(recipe.id.toString())
        recipeDao.deleteRecipe(recipe)
        nutrientsData?.let { recipeDao.deleteNutrients(it) }
    }

    suspend fun getSimilarData(id: String): ResponseState<RecipeData> {
        val response = api.getSimilar(id = id)
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