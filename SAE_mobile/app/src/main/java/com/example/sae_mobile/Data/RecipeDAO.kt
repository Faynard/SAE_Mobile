package com.example.sae_mobile.data

import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.network.ApiService

class RecipeDAO(private val apiService: ApiService) {

    suspend fun fetchRecipes(apiKey: String): List<Recipe> {
        return apiService.fetchRecipes(apiKey)
    }

    suspend fun fetchFilteredRecipes(apiKey: String,genre:String): List<Recipe> {
        return apiService.fetchFilteredRecipes(apiKey,genre)
    }
}
