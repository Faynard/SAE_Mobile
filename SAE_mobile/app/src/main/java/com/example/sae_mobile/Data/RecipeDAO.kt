package com.example.sae_mobile.data

import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.network.ApiService

class RecipeDAO(private val apiService: ApiService) {

    suspend fun fetchRecipes(apiKey: String): List<Recipe> {
        return apiService.fetchRecipes(apiKey)
    }

    suspend fun fetchFilteredRecipes(apiKey: String,name:String, genre:String, portion : Int): List<Recipe> {
        return apiService.fetchFilteredRecipes(apiKey, name, genre, portion)
    }
}
