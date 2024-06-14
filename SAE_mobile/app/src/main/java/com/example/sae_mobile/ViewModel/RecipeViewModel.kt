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
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun fetchRecipes(apiKey: String) {
        viewModelScope.launch {
            val recipesList = recipeDao.fetchRecipes(apiKey)
            _recipes.postValue(recipesList)
        }
    }
}
