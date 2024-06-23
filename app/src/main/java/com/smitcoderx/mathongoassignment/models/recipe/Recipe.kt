package com.smitcoderx.mathongoassignment.models.recipe


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
data class Recipe(
    @SerializedName("aggregateLikes")
    var aggregateLikes: Int? = null,
    @SerializedName("analyzedInstructions")
    var analyzedInstructions: List<AnalyzedInstruction?>? = null,
    @SerializedName("cheap")
    var cheap: Boolean? = null,
    @SerializedName("cookingMinutes")
    var cookingMinutes: Int? = null,
    @SerializedName("creditsText")
    var creditsText: String? = null,
    @SerializedName("cuisines")
    var cuisines: List<String?>? = null,
    @SerializedName("dairyFree")
    var dairyFree: Boolean? = null,
    @SerializedName("diets")
    var diets: List<String?>? = null,
    @SerializedName("dishTypes")
    var dishTypes: List<String?>? = null,
    @SerializedName("extendedIngredients")
    var extendedIngredients: List<ExtendedIngredient?>? = null,
    @SerializedName("gaps")
    var gaps: String? = null,
    @SerializedName("glutenFree")
    var glutenFree: Boolean? = null,
    @SerializedName("healthScore")
    var healthScore: Int? = null,
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("imageType")
    var imageType: String? = null,
    @SerializedName("instructions")
    var instructions: String? = null,
    @SerializedName("license")
    var license: String? = null,
    @SerializedName("lowFodmap")
    var lowFodmap: Boolean? = null,
    @SerializedName("occasions")
    var occasions: List<String?>? = null,
    @SerializedName("originalId")
    var originalId: String? = null,
    @SerializedName("preparationMinutes")
    var preparationMinutes: Int? = null,
    @SerializedName("pricePerServing")
    var pricePerServing: Double? = null,
    @SerializedName("readyInMinutes")
    var readyInMinutes: Int? = null,
    @SerializedName("servings")
    var servings: Int? = null,
    @SerializedName("sourceName")
    var sourceName: String? = null,
    @SerializedName("sourceUrl")
    var sourceUrl: String? = null,
    @SerializedName("spoonacularScore")
    var spoonacularScore: Double? = null,
    @SerializedName("spoonacularSourceUrl")
    var spoonacularSourceUrl: String? = null,
    @SerializedName("summary")
    var summary: String? = null,
    @SerializedName("sustainable")
    var sustainable: Boolean? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("vegan")
    var vegan: Boolean? = null,
    @SerializedName("vegetarian")
    var vegetarian: Boolean? = null,
    @SerializedName("veryHealthy")
    var veryHealthy: Boolean? = null,
    @SerializedName("veryPopular")
    var veryPopular: Boolean? = null,
    @SerializedName("weightWatcherSmartPoints")
    var weightWatcherSmartPoints: Int? = null,
    var isFavourite: Boolean = false
)