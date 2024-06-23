package com.smitcoderx.mathongoassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smitcoderx.mathongoassignment.models.nutrition.NutritionData
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import com.smitcoderx.mathongoassignment.utils.Converters

@Database(entities = [Recipe::class, NutritionData::class], version = 1)

@TypeConverters(Converters::class)
abstract class ReciipiieDatabase : RoomDatabase() {

    abstract fun recipeDao(): ReciipiieDao
}