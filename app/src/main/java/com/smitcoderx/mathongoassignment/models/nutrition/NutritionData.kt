package com.smitcoderx.mathongoassignment.models.nutrition


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "nutrients")

data class NutritionData(
    @SerializedName("calories") var calories: String? = "",
    @SerializedName("carbs") var carbs: String? = "",
    @SerializedName("expires") var expires: Long? = 0,
    @SerializedName("fat") var fat: String? = "",
    @SerializedName("flavonoids") var flavonoids: List<Flavonoid>? = listOf(),
    @SerializedName("good") var good: List<Good>? = listOf(),
    @SerializedName("bad") var bad: List<Bad>? = listOf(),
    @SerializedName("ingredients") var ingredients: List<Ingredient>? = listOf(),
    @SerializedName("isStale") var isStale: Boolean? = false,
    @SerializedName("caloricBreakdown") var caloricBreakdown: CaloricBreakdown? = CaloricBreakdown(),
    @SerializedName("nutrients") var nutrients: List<NutrientX>? = listOf(),
    @SerializedName("properties") var properties: List<Property>? = listOf(),
    @SerializedName("protein") var protein: String? = "",
    @SerializedName("weightPerServing") var weightPerServing: WeightPerServing? = WeightPerServing(),
    @PrimaryKey var recipeId: String
)