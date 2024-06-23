package com.smitcoderx.mathongoassignment.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smitcoderx.mathongoassignment.models.nutrition.Bad
import com.smitcoderx.mathongoassignment.models.nutrition.CaloricBreakdown
import com.smitcoderx.mathongoassignment.models.nutrition.Flavonoid
import com.smitcoderx.mathongoassignment.models.nutrition.Good
import com.smitcoderx.mathongoassignment.models.nutrition.Ingredient
import com.smitcoderx.mathongoassignment.models.nutrition.NutrientX
import com.smitcoderx.mathongoassignment.models.nutrition.Property
import com.smitcoderx.mathongoassignment.models.nutrition.WeightPerServing
import com.smitcoderx.mathongoassignment.models.recipe.AnalyzedInstruction
import com.smitcoderx.mathongoassignment.models.recipe.ExtendedIngredient

class Converters {
    @TypeConverter
    fun fromAnalyzedInstructionString(value: String?): List<AnalyzedInstruction> {
        val listType = object : TypeToken<List<AnalyzedInstruction?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromAnalyzedInstructionList(list: List<AnalyzedInstruction?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromExtendedIngredientString(value: String?): List<ExtendedIngredient> {
        val listType = object : TypeToken<List<ExtendedIngredient?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromExtendedIngredientList(list: List<ExtendedIngredient?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromCaloricBreakdownString(value: String?): CaloricBreakdown {
        val listType = object : TypeToken<CaloricBreakdown?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCaloricBreakdown(list: CaloricBreakdown?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromFlavnoidString(value: String?): List<Flavonoid> {
        val listType = object : TypeToken<List<Flavonoid?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromFlavnoidList(list: List<Flavonoid?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromGoodString(value: String?): List<Good> {
        val listType = object : TypeToken<List<Good?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromGoodList(list: List<Good?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromBadString(value: String?): List<Bad> {
        val listType = object : TypeToken<List<Bad?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromBadList(list: List<Bad?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromIngredientString(value: String?): List<Ingredient> {
        val listType = object : TypeToken<List<Ingredient?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromIngredientList(list: List<Ingredient?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromNutrientXString(value: String?): List<NutrientX> {
        val listType = object : TypeToken<List<NutrientX?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromNutrientXList(list: List<NutrientX?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromPropertyString(value: String?): List<Property> {
        val listType = object : TypeToken<List<Property?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromPropertyList(list: List<Property?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun fromWeightPerServingString(value: String?): WeightPerServing {
        val listType = object : TypeToken<WeightPerServing?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromWeightPerServing(list: WeightPerServing?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}