package com.example.sae_mobile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sae_mobile.Model.Recipe
import com.example.sae_mobile.data.RecipeDAO
import kotlinx.coroutines.launch

class RecipeViewModel(private val recipeDao: RecipeDAO) : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    private val _filteredRecipes = MutableLiveData<List<Recipe>>()

    val recipes: LiveData<List<Recipe>> get() = _recipes
    val filteredRecipes: LiveData<List<Recipe>> get() = _filteredRecipes


    fun fetchRecipes(apiKey: String) {
        viewModelScope.launch {
            val recipesList = recipeDao.fetchRecipes(apiKey)
            _recipes.postValue(recipesList)
        }
    }

    fun fetchFilteredRecipes(apiKey: String,genre:String) {
        viewModelScope.launch {
            val filteredRecipesList = recipeDao.fetchFilteredRecipes(apiKey,genre)
            _filteredRecipes.postValue(filteredRecipesList)
        }
    }
}
