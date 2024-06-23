package com.smitcoderx.mathongoassignment.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe

@Dao
interface ReciipiieDao {

    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    suspend fun getRecipeById(recipeId: String): Recipe?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM nutrients WHERE recipeId = :id")
    suspend fun getNutrientsById(id: String): NutritionData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrients(nutrients: NutritionData)

    @Delete
    suspend fun deleteNutrients(nutrientX: NutritionData)


}