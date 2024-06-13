package com.example.sae_mobile.Model

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val vegetarian: Boolean,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val dairyFree: Boolean,
    val veryHealthy: Boolean,
    val cheap: Boolean,
    val veryPopular: Boolean,
    val sustainable: Boolean,
    val lowFodmap: Boolean,
    val weightWatcherSmartPoints: Int?,
    val gaps: String,
    val preparationMinutes: Int?,
    val cookingMinutes: Int?,
    val aggregateLikes: Int,
    val healthScore: Int,
    val creditsText: String,
    val sourceName: String,
    val pricePerServing: Double,
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val image: String,
    val imageType: String,
    val summary: String,
    val cuisines: List<String>,
    val dishTypes: List<String>,
    val diets: List<String>,
    val occasions: List<String>,
    val spoonacularScore: Double,
    val spoonacularSourceUrl: String
)
