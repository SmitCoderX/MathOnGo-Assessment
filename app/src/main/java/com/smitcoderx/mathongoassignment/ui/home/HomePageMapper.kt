package com.smitcoderx.mathongoassignment.ui.home

import com.smitcoderx.mathongoassignment.models.recipe.RecipeData
import com.smitcoderx.mathongoassignment.models.search.SearchData
import com.smitcoderx.mathongoassignment.models.user.UserData

data class HomePageMapper(
    val loggedInUser: UserData,
    val popularRecipes: RecipeData,
    val allRecipes: SearchData
)